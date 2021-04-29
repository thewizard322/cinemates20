package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Controller.Fragment.RegistrazioneController;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Activity.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrazioneFragment extends Fragment {

    private Button btRegistrati;
    private TextInputLayout tilUsernameReg;
    private TextInputLayout tilPasswordReg;
    private TextInputLayout tilEmailReg;
    private ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registrazione,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilUsernameReg = view.findViewById(R.id.tilUserNameReg);
        tilPasswordReg = view.findViewById(R.id.tilPasswordReg);
        tilEmailReg = view.findViewById(R.id.tilEmail);
        btRegistrati = view.findViewById(R.id.btRegistrati);
        initializeProgressDialog();
        new RegistrazioneController(this);
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public TextInputLayout getTilUsernameReg(){
        return tilUsernameReg;
    }

    public TextInputLayout getTilPasswordReg(){
        return tilPasswordReg;
    }

    public TextInputLayout getTilEmailReg(){
        return tilEmailReg;
    }

    public Button getBtRegistrati(){
        return btRegistrati;
    }

    public ProgressDialog getProgressDialogCaricamento(){
        return progressDialogCaricamento;
    }

    public String getUserName(){
        return tilUsernameReg.getEditText().getText().toString();
    }

    public String getPassword(){
        return tilPasswordReg.getEditText().getText().toString();
    }

    public String getEmail(){
        return tilEmailReg.getEditText().getText().toString();
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getApplicationWindowToken(),0);
    }
}
