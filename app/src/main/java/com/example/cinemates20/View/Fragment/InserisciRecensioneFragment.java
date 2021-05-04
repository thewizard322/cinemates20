package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
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

    public ProgressDialog getProgressDialogRecensione(){
        return progressDialogRecensione;
    }

    public Spinner getSpinnerVoto() {
        return spinnerVoto;
    }

    public TextInputLayout getTilTestoRecensioneInserisciRecensione() {
        return tilTestoRecensioneInserisciRecensione;
    }

    public Button getbRececensisciInserisciRecensione() {
        return bRececensisciInserisciRecensione;
    }
}
