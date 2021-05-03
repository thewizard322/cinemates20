package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.LoginFragment;
import com.example.cinemates20.View.Fragment.RegistrazioneFragment;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrazionePresenter {

    private RegistrazioneFragment registrazioneFragment;

    public RegistrazionePresenter(RegistrazioneFragment registrazioneFragment){
        this.registrazioneFragment = registrazioneFragment;
        setupFLoatingLabelError();
        initializeListener();
    }

    private void initializeListener(){
        Button btRegistrazione = registrazioneFragment.getBtRegistrati();
        TextInputLayout tilEmailReg = registrazioneFragment.getTilEmailReg();
        btRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrazioneFragment.hideKeyboard();
                onRegister();
            }
        });
        tilEmailReg.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                    btRegistrazione.performClick();
                return false;
            }
        });
    }

    private void onRegisterSuccess(){
        registrazioneFragment.mostraToast("Utente registrato con successo");
        backToLoginFragment();
    }

    private void onRegisterError(int err){
        if(err==1) {
            registrazioneFragment.mostraToast("Inserire username");
            TextInputLayout tilUsernameReg = registrazioneFragment.getTilUsernameReg();
            tilUsernameReg.setError("Inserire username");
            tilUsernameReg.setErrorEnabled(true);
        }
        else if(err==2) {
            registrazioneFragment.mostraToast("Inserire password");
            TextInputLayout tilPasswordReg = registrazioneFragment.getTilPasswordReg();
            tilPasswordReg.setError("Inserire password");
            tilPasswordReg.setErrorEnabled(true);
        }
        else if(err==3)
            registrazioneFragment.mostraToast("Email non valida");
        else if(err==4)
            registrazioneFragment.mostraToast("Inserire password di almeno 6 caratteri");
        else if(err==5) {
            registrazioneFragment.mostraToast("Inserire e-mail");
            TextInputLayout tilEmailReg = registrazioneFragment.getTilEmailReg();
            tilEmailReg.setError("Inserire e-mail");
            tilEmailReg.setErrorEnabled(true);
        }
        else if(err==6)
            registrazioneFragment.mostraToast("Inserire username di almeno 5 caratteri");
        else if(err==7)
            registrazioneFragment.mostraToast("Username non valido");
    }

    private void onRegisterErrorDB(int err){
        if(err==1)
            registrazioneFragment.mostraToast("Impossibile registrarsi");
        else if(err==2)
            registrazioneFragment.mostraToast("Impossibile connettersi alla rete");
        else if(err==3) {
            registrazioneFragment.mostraToast("Username già utilizzato");
            TextInputLayout tilUsernameReg = registrazioneFragment.getTilUsernameReg();
            tilUsernameReg.setError("Username già utilizzato");
            tilUsernameReg.setErrorEnabled(true);
        }
        else if(err==4) {
            registrazioneFragment.mostraToast("E-mail già utilizzata");
            TextInputLayout tilEmailReg = registrazioneFragment.getTilEmailReg();
            tilEmailReg.setError("E-mail già utilizzata");
            tilEmailReg.setErrorEnabled(true);
        }
    }

    private void backToLoginFragment() {
        FragmentManager fm = registrazioneFragment.getActivity().getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    private void onRegister(){
        String username = registrazioneFragment.getUserName();
        String pwd = registrazioneFragment.getPassword();
        String email = registrazioneFragment.getEmail();
        Utente utente = new Utente(username,pwd,email);
        int validate = utente.isValidate();
        if(validate!=0) {
            onRegisterError(validate);
        }
        else {
            RegisterTask registerTask = new RegisterTask();
            registerTask.execute(username, pwd, email);
        }
    }

    private class RegisterTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            registrazioneFragment.getProgressDialogCaricamento().show();
        }

        @Override
        protected String doInBackground(String... param) {
            UtenteDAO utenteDAO = new UtenteDAO();

            int checkUser = utenteDAO.checkUser(param[0]);
            if(checkUser == 1)
                return "userAlreadyUsed";
            else if (checkUser == 2 || checkUser == 3)
                return "noconn";

            int checkEmail = utenteDAO.checkEmail(param[2]);
            if(checkEmail == 1)
                return "emailAlreadyUsed";
            else if (checkEmail == 2 || checkEmail == 3)
                return "noconn";

            int reg = utenteDAO.registerUser(param[0],param[1],param[2]);
            if(reg == 1)
                return "noreg";
            else if(reg == 2)
                return "noconn";
            return "ok";
        }

        @Override
        protected void onPostExecute(String s) {
            registrazioneFragment.getProgressDialogCaricamento().dismiss();
            if(s.equals("ok"))
                onRegisterSuccess();
            else if(s.equals("noreg"))
                onRegisterErrorDB(1);
            else if(s.equals("noconn"))
                onRegisterErrorDB(2);
            else if(s.equals("userAlreadyUsed"))
                onRegisterErrorDB(3);
            else if(s.equals("emailAlreadyUsed"))
                onRegisterErrorDB(4);
        }
    }

    private void setupFLoatingLabelError(){
        TextInputLayout tilUsernameReg = registrazioneFragment.getTilUsernameReg();
        TextInputLayout tilPasswordReg = registrazioneFragment.getTilPasswordReg();
        TextInputLayout tilEmailReg = registrazioneFragment.getTilEmailReg();
        tilUsernameReg.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    tilUsernameReg.setError("Inserire username");
                    tilUsernameReg.setErrorEnabled(true);
                }
                else if(s.length()<5){
                    tilUsernameReg.setError("Inserire almeno 5 caratteri");
                    tilUsernameReg.setErrorEnabled(true);
                }
                else if(tilUsernameReg.getEditText().getText().toString().matches(".*\\s.*")){
                    tilUsernameReg.setError("Username non valido");
                    tilUsernameReg.setErrorEnabled(true);
                }
                else{
                    tilUsernameReg.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilPasswordReg.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    tilPasswordReg.setError("Inserire password");
                    tilPasswordReg.setErrorEnabled(true);
                }
                else if(s.length()<6){
                    tilPasswordReg.setError("Inserire almeno 6 caratteri");
                    tilPasswordReg.setErrorEnabled(true);
                }
                else{
                    tilPasswordReg.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilEmailReg.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    tilEmailReg.setError("Inserire e-mail");
                    tilEmailReg.setErrorEnabled(true);
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    tilEmailReg.setError("Inserire un'e-mail valida");
                    tilEmailReg.setErrorEnabled(true);
                }
                else{
                    tilEmailReg.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}