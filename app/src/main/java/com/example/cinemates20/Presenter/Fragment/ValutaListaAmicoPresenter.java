package com.example.cinemates20.Presenter.Fragment;

import com.example.cinemates20.View.Fragment.ValutaListaAmicoFragment;

public class ValutaListaAmicoPresenter {

    private ValutaListaAmicoFragment valutaListaAmicoFragment;
    private String titoloLista;

    public ValutaListaAmicoPresenter(ValutaListaAmicoFragment valutaListaAmicoFragment, String titoloLista){
        this.valutaListaAmicoFragment = valutaListaAmicoFragment;
        this.titoloLista = titoloLista;
        initializeListener();
    }

    private void initializeListener(){

    }

}
