package com.example.cinemates20.Presenter.Fragment;

import android.view.View;

import com.example.cinemates20.View.Fragment.VisualizzaValutazioniProprieListePersonalizzateFragment;

public class VisualizzaValutazioniProprieListePersonalizzatePresenter {

    private VisualizzaValutazioniProprieListePersonalizzateFragment visualizzaValutazioniProprieListePersonalizzateFragment;
    private String titoloListaNotificaSelezionata;
    private String usernameMittenteListaNotificaSelezionata;
    private int likeOrDislike;
    private String commento;
    private String valutazioneRapida;

    public VisualizzaValutazioniProprieListePersonalizzatePresenter(VisualizzaValutazioniProprieListePersonalizzateFragment visualizzaValutazioniProprieListePersonalizzateFragment, String titoloLista, String usernameMittente, int likeOrDislike, String commento) {
        this.visualizzaValutazioniProprieListePersonalizzateFragment = visualizzaValutazioniProprieListePersonalizzateFragment;
        this.titoloListaNotificaSelezionata = titoloLista;
        this.usernameMittenteListaNotificaSelezionata = usernameMittente;
        this.likeOrDislike = likeOrDislike;
        this.commento = commento;
        visualizzaValutazioniProprieListePersonalizzateFragment.getTvLabelValutazioneVisualizzaValutazioniProprieListePersonalizzate().setVisibility(View.GONE);
        visualizzaValutazioniProprieListePersonalizzateFragment.getTvTestoValutazioneVisualizzaValutazioniProprieListePersonalizzate().setVisibility(View.GONE);
        if(!((commento == null) || (commento.equals("")))){
            visualizzaValutazioniProprieListePersonalizzateFragment.getTvLabelValutazioneVisualizzaValutazioniProprieListePersonalizzate().setVisibility(View.VISIBLE);
            visualizzaValutazioniProprieListePersonalizzateFragment.getTvTestoValutazioneVisualizzaValutazioniProprieListePersonalizzate().setVisibility(View.VISIBLE);
        }
        if(likeOrDislike == 0) {
            this.valutazioneRapida = " non ";
        }
        else if(likeOrDislike == 1){
            this.valutazioneRapida = " ";
        }

        visualizzaValutazioniProprieListePersonalizzateFragment.setTvIntestazioneVisualizzaValutazioniProprieListePersonalizzate(titoloLista);
        visualizzaValutazioniProprieListePersonalizzateFragment.setTextTestoValutazione(commento);
        visualizzaValutazioniProprieListePersonalizzateFragment.setTextRapida("All'utente " + usernameMittenteListaNotificaSelezionata +
                valutazioneRapida + "è piaciuta la tua lista '" + titoloListaNotificaSelezionata +"'");
    }


}
