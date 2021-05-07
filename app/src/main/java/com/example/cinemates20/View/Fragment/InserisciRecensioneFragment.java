package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Presenter.Fragment.InserisciRecensionePresenter;
import com.example.cinemates20.R;
import com.google.android.material.textfield.TextInputLayout;

public class InserisciRecensioneFragment extends Fragment {

    private Spinner spinnerVoto;
    private TextInputLayout tilTestoRecensioneInserisciRecensione;
    private Button bRececensisciInserisciRecensione;
    private TextView tvFilmInserisciRecensione;
    private ProgressDialog progressDialogRecensione;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inseriscirecensione,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerVoto = view.findViewById(R.id.spinnerValutazioneRecensione);
        tilTestoRecensioneInserisciRecensione = view.findViewById(R.id.tilTestoRecensioneInserisciRecensione);
        bRececensisciInserisciRecensione = view.findViewById(R.id.bRececensisciInserisciRecensione);
        tvFilmInserisciRecensione = view.findViewById(R.id.tvFilmInserisciRecensione);
        initializeProgressDialog();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new InserisciRecensionePresenter(this,film);
        }
    }

    private void initializeProgressDialog(){
        progressDialogRecensione = new ProgressDialog(getActivity());
        progressDialogRecensione.setTitle("");
        progressDialogRecensione.setMessage("Invio recensione");
        progressDialogRecensione.setCancelable(false);
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public void mostraAlertDialogOk(String titolo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(titolo)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void mostraProgressDialogRecensione(){
        progressDialogRecensione.show();
    }

    public void togliProgressDialogRecensione(){
        progressDialogRecensione.dismiss();
    }

    public Spinner getSpinnerVoto() {
        return spinnerVoto;
    }

    public String getVoto(){
        return spinnerVoto.getSelectedItem().toString();
    }

    public TextInputLayout getTilTestoRecensioneInserisciRecensione() {
        return tilTestoRecensioneInserisciRecensione;
    }

    public String getTestoRecensione(){
        return tilTestoRecensioneInserisciRecensione.getEditText().getText().toString();
    }

    public Button getbRececensisciInserisciRecensione() {
        return bRececensisciInserisciRecensione;
    }

    public void setTvFilmInserisciRecensione(String titolo){
        tvFilmInserisciRecensione.setText("Stai recensendo '"+
                titolo+"'");
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getApplicationWindowToken(),0);
    }

}
