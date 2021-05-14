package com.example.cinemates20.Presenter.Fragment;

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
        if((commento == null) || (commento.equals(""))){
            commento = "Commento non inserito...";
        }
        if(likeOrDislike == 0) {
            this.valutazioneRapida = "DISLIKE";
        }
        else if(likeOrDislike == 1){
            this.valutazioneRapida = "LIKE";
        }

        visualizzaValutazioniProprieListePersonalizzateFragment.setTvIntestazioneVisualizzaValutazioniProprieListePersonalizzate(titoloLista);
        visualizzaValutazioniProprieListePersonalizzateFragment.setTextTestoValutazione(commento);
        visualizzaValutazioniProprieListePersonalizzateFragment.setTextRapida(valutazioneRapida);
    }


}
