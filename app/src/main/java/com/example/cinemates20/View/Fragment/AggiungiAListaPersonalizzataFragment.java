package com.example.cinemates20.View.Fragment;

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
import com.example.cinemates20.Presenter.Fragment.InserisciRecensionePresenter;
import com.example.cinemates20.R;

public class AggiungiAListaPersonalizzataFragment extends Fragment {

    Spinner spinnerTitoloListaPersAggiungiAListaPers;
    TextView tvDescrizioneListaAggiungiAListaPers;
    Button bAggiungiFilmAListaPers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungialistapersonalizzata,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerTitoloListaPersAggiungiAListaPers = view.findViewById(R.id.spinnerTitoloListaPersAggiungiAListaPers);
        tvDescrizioneListaAggiungiAListaPers = view.findViewById(R.id.tvDescrizioneListaAggiungiAListaPers);
        bAggiungiFilmAListaPers = view.findViewById(R.id.bAggiungiFilmAListaPers);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new AggiungiAListaPersonalizzataPresenter(this,film);
        }

    }

    public Spinner getSpinnerTitoloListaPersAggiungiAListaPers() {
        return spinnerTitoloListaPersAggiungiAListaPers;
    }

    public TextView getTvDescrizioneListaAggiungiAListaPers() {
        return tvDescrizioneListaAggiungiAListaPers;
    }

    public Button getbAggiungiFilmAListaPers() {
        return bAggiungiFilmAListaPers;
    }

    public void setTextDescrizione(String descrizione){
        tvDescrizioneListaAggiungiAListaPers.setText(descrizione);
    }

}
