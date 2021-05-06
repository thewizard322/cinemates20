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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Presenter.Fragment.AggiungiAListaPersonalizzataPresenter;
import com.example.cinemates20.R;

public class AggiungiAListaPersonalizzataFragment extends Fragment {

    TextView tvTitoloFilmAggiungiAPers;
    Spinner spinnerTitoloListaPersAggiungiAListaPers;
    TextView tvDescrizioneTitoloAggiungiAListaPers;
    TextView tvDescrizioneListaAggiungiAListaPers;
    Button bAggiungiFilmAListaPers;
    ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungialistapersonalizzata,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitoloFilmAggiungiAPers = view.findViewById(R.id.tvTitoloFilmAggiungiAPers);
        spinnerTitoloListaPersAggiungiAListaPers = view.findViewById(R.id.spinnerTitoloListaPersAggiungiAListaPers);
        tvDescrizioneTitoloAggiungiAListaPers = view.findViewById(R.id.tvDescrizioneTitoloAggiungiAListaPers);
        tvDescrizioneListaAggiungiAListaPers = view.findViewById(R.id.tvDescrizioneListaAggiungiAListaPers);
        bAggiungiFilmAListaPers = view.findViewById(R.id.bAggiungiFilmAListaPers);
        initializeProgressDialog();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new AggiungiAListaPersonalizzataPresenter(this,film);
        }

    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public Spinner getSpinnerTitoloListaPersAggiungiAListaPers() {
        return spinnerTitoloListaPersAggiungiAListaPers;
    }

    public Button getbAggiungiFilmAListaPers() {
        return bAggiungiFilmAListaPers;
    }

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgrssDialogCaricamento(){
        progressDialogCaricamento.dismiss();
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

    public void setTextDescrizione(String descrizione){
        tvDescrizioneListaAggiungiAListaPers.setText(descrizione);
    }

    public void setTextTitoloFilmAggiungiAPers(String titoloFilm){
        tvTitoloFilmAggiungiAPers.setText("Stai aggiungendo '"+titoloFilm+"'");
    }

    public void setVisibilityTvDescrizioneTitoloAggiungiAListaPers(int visibility){
        tvDescrizioneTitoloAggiungiAListaPers.setVisibility(visibility);
    }

    public void setVisibilityTvDescrizioneListaAggiungiAListaPers(int visibility){
        tvDescrizioneListaAggiungiAListaPers.setVisibility(visibility);
    }

}
