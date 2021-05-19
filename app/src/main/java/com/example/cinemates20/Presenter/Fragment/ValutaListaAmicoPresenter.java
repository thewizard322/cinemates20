package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.ValutaListaAmicoFragment;

public class ValutaListaAmicoPresenter {

    private ValutaListaAmicoFragment valutaListaAmicoFragment;
    private String titoloLista;
    private String utenteLista;

    public ValutaListaAmicoPresenter(ValutaListaAmicoFragment valutaListaAmicoFragment, String titoloLista,
                                     String utenteLista){
        this.valutaListaAmicoFragment = valutaListaAmicoFragment;
        this.titoloLista = titoloLista;
        this.utenteLista = utenteLista;
        valutaListaAmicoFragment.setTextTvTitoloListaValuta(titoloLista);
        initializeListener();
    }

    private void initializeListener(){
        Button bValutaListaAmico = valutaListaAmicoFragment.getbValutaListaAmico();
        bValutaListaAmico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valutaListaAmicoFragment.hideKeyboard();
                inviaValutazione();
            }
        });
    }

    private void inviaValutazione(){
        if(!valutaListaAmicoFragment.rbDislikeIsChecked() && !valutaListaAmicoFragment.rbLikeIsChecked())
            valutaListaAmicoFragment.mostraToast("Selezionare like o dislike");
        else{
            CheckValutazioneGiaInviata checkValutazioneGiaInviata = new CheckValutazioneGiaInviata();
            checkValutazioneGiaInviata.execute();
        }
    }

    private void backToVisualizzaListePersonalizzateAmiciFragment(){
        FragmentManager fm = valutaListaAmicoFragment.getActivity().getSupportFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    private class CheckValutazioneGiaInviata extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            valutaListaAmicoFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String mittente = Utente.getUtenteLoggato().getUsername();
            NotificaDAO notificaDAO = new NotificaDAO();
            return notificaDAO.checkValutazioneGiaInviata(mittente,utenteLista,titoloLista);
        }

        @Override
        protected void onPostExecute(Boolean valNonInviata) {
            if(valNonInviata==false){
                valutaListaAmicoFragment.togliProgressDialogCaricamento();
                valutaListaAmicoFragment.mostraAlertDialogOk("ERRORE", "Hai già valutato questa lista");
                backToVisualizzaListePersonalizzateAmiciFragment();
            }
            else{
                InviaValutazioneListaTask inviaValutazioneListaTask = new InviaValutazioneListaTask();
                inviaValutazioneListaTask.execute();
            }
        }
    }

    private class InviaValutazioneListaTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            String mittente = Utente.getUtenteLoggato().getUsername();
            String commentoValutazione = valutaListaAmicoFragment.getCommentoValutazone();
            int likeOrDislike = 0;
            if(valutaListaAmicoFragment.rbLikeIsChecked())
                likeOrDislike = 1;
            else if(valutaListaAmicoFragment.rbDislikeIsChecked())
                likeOrDislike = 0;
            NotificaDAO notificaDAO = new NotificaDAO();
            return notificaDAO.inviaNotificaValutazioneListaAmico(mittente,utenteLista,titoloLista,likeOrDislike,commentoValutazione);
        }

        @Override
        protected void onPostExecute(Boolean notificaInviata) {
            valutaListaAmicoFragment.togliProgressDialogCaricamento();
            if(notificaInviata==true)
                valutaListaAmicoFragment.mostraAlertDialogOk("LISTA COMMENTATA", "Lista commentata con successo");
            else
                valutaListaAmicoFragment.mostraAlertDialogOk("ERRORE","Impossibile commentare la lista");
            backToVisualizzaListePersonalizzateAmiciFragment();
        }
    }

}
