package com.example.cinemates20.Presenter.Fragment;

import android.view.View;
import android.widget.Button;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.View.Fragment.RaccomandaPreferitoFragment;

public class RaccomandaPreferitoPresenter {

    RaccomandaPreferitoFragment raccomandaPreferitoFragment;
    Film filmSelezionato;


    public RaccomandaPreferitoPresenter(RaccomandaPreferitoFragment raccomandaPreferitoFragment, Film film){
        this.raccomandaPreferitoFragment = raccomandaPreferitoFragment;
        this.filmSelezionato = film;
        initializeListener();
    }

    private void initializeListener(){
        Button bInviaRaccomandaPreferito = raccomandaPreferitoFragment.getbInviaRaccomandaPreferito();
        bInviaRaccomandaPreferito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void raccomandaFilmPreferito(){

    }

}
