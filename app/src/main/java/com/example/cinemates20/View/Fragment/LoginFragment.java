package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.LoginPresenter;
import com.example.cinemates20.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    private TextInputLayout tilUsernameLgn;
    private TextInputLayout tilPasswordLgn;
    private TextView txtRegistrazioneLgn;
    private Button btLogin;
    private ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilUsernameLgn = view.findViewById(R.id.tilUserNameLgn);
        tilPasswordLgn = view.findViewById(R.id.tilPasswordLgn);
        btLogin = view.findViewById(R.id.btLogin);
        txtRegistrazioneLgn = view.findViewById(R.id.txtRegistrazioneLgn);
        initializeProgressDialog();
        new LoginPresenter(this);
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public TextView getTxtRegistrazioneLgn(){
        return txtRegistrazioneLgn;
    }

    public Button getBtLogin(){
        return btLogin;
    }

    public TextInputLayout getTilPasswordLogin(){
        return tilPasswordLgn;
    }

    public ProgressDialog getProgressDialogCaricamento(){
        return progressDialogCaricamento;
    }

    public String getUserName(){
        return tilUsernameLgn.getEditText().getText().toString();
    }

    public String getPassword(){
        return tilPasswordLgn.getEditText().getText().toString();
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
