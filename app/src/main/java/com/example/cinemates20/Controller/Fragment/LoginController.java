package com.example.cinemates20.Controller.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.example.cinemates20.View.Fragment.HomeFragment;
import com.example.cinemates20.View.Fragment.LoginFragment;
import com.example.cinemates20.View.Fragment.RegistrazioneFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class LoginController {

    private LoginFragment loginFragment;

    public LoginController(LoginFragment loginFragment){
        this.loginFragment=loginFragment;
        initializeListener();
    }

    private void initializeListener(){
        TextView txtReg = loginFragment.getTxtRegistrazioneLgn();
        Button btLogin = loginFragment.getBtLogin();
        TextInputLayout tilPasswordLgn = loginFragment.getTilPasswordLogin();

        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRegisterFragment();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFragment.hideKeyboard();
                onLogin();
            }
        });

        tilPasswordLgn.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                    btLogin.performClick();
                return false;
            }
        });
    }

    private void replaceRegisterFragment() {
        Fragment fg = new RegistrazioneFragment();
        FragmentManager fm = loginFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void onLogin(){
        String username = loginFragment.getUserName();
        String pwd = loginFragment.getPassword();
        LoginTask loginTask = new LoginTask();
        loginTask.execute(username, pwd);
    }

    public void prelevaDatiUtente(){
        String username = loginFragment.getUserName();
        PrelevaDatiUtenteTask prelevaDatiUtenteTask = new PrelevaDatiUtenteTask();
        prelevaDatiUtenteTask.execute(username);
    }

    private void onLoginSuccess(Utente utenteLoggato){
        Utente.setUtenteLoggato(utenteLoggato);
        saveLoginUser(utenteLoggato);
        replaceFragmentHome();
    }

    public void saveLoginUser(Utente utenteLoggato){
        SharedPreferences sharedPreferences = loginFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(utenteLoggato);
        prefsEditor.putString("utenteLoggato",json);
        prefsEditor.commit();
    }

    public void replaceFragmentHome(){
        Fragment fg = new HomeFragment();
        FragmentManager fm = loginFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).commit();
    }

    private void onLoginError(){
        loginFragment.mostraToast("Username o password errati");
    }

    private void onLoginErrorDB(int err){
        if(err == 2)
            loginFragment.mostraToast("Impossibile loggarsi");
        else if(err == 3)
            loginFragment.mostraToast("Impossibile connettersi alla rete");
        else if(err == 4)
            loginFragment.mostraToast("Impossibile recuperare i dati dell'utente");
    }

    private class LoginTask extends AsyncTask<String,Void,Integer>{

        @Override
        protected void onPreExecute() {
            loginFragment.getProgressDialogCaricamento().show();
        }

        @Override
        protected Integer doInBackground(String... param) {
            UtenteDAO utenteDAO = new UtenteDAO();
            int loginRet =  utenteDAO.login(param[0],param[1]);
            return loginRet;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(integer == 0)
                prelevaDatiUtente();
            else {
                loginFragment.getProgressDialogCaricamento().dismiss();
                if (integer == 1)
                    onLoginError();
                else if (integer == 2)
                    onLoginErrorDB(2);
                else if (integer == 3)
                    onLoginErrorDB(3);
            }
        }
    }

        private class PrelevaDatiUtenteTask extends AsyncTask<String,Void,Utente>{

        @Override
        protected Utente doInBackground(String... param) {
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente utenteLoggato = utenteDAO.prelevaUtente(param[0]);
            return utenteLoggato;
        }

            @Override
            protected void onPostExecute(Utente utenteLoggato) {
                loginFragment.getProgressDialogCaricamento().dismiss();
                if(utenteLoggato == null)
                    onLoginErrorDB(4);
                else
                    onLoginSuccess(utenteLoggato);
            }
        }

}
