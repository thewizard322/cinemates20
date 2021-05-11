package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.RaccomandaListaPersonalizzataPresenter;
import com.example.cinemates20.R;

public class RaccomandaListaPersonalizzataFragment extends Fragment {

    TextView tvTitoloRaccomandaLista;
    Spinner spinnerAmicoRaccomandaLista;
    Button bInviaRaccomandaLista;
    ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raccomandalistapersonalizzata,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitoloRaccomandaLista = view.findViewById(R.id.tvTitoloRaccomandaLista);
        spinnerAmicoRaccomandaLista = view.findViewById(R.id.spinnerAmicoRaccomandaLista);
        bInviaRaccomandaLista = view.findViewById(R.id.bInviaRaccomandaLista);
        initializeProgressDialog();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String titoloLista = bundle.getString("titoloLista");
            new RaccomandaListaPersonalizzataPresenter(this,titoloLista);
        }
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public Button getbInviaRaccomandaLista(){
        return bInviaRaccomandaLista;
    }

    public String getUsernameAmico(){
        if(spinnerAmicoRaccomandaLista.getSelectedItem()!=null)
            return spinnerAmicoRaccomandaLista.getSelectedItem().toString();
        return "";
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

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgrssDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public void setAdapterSpinnerTitoloLista(ArrayAdapter<String> arrayAdapter){
        spinnerAmicoRaccomandaLista.setAdapter(arrayAdapter);
    }

    public void setTvTitoloRaccomandaPreferito(String titoloLista){
        tvTitoloRaccomandaLista.setText("Condividi la lista '"+titoloLista+"' ai tuoi amici");
    }

}