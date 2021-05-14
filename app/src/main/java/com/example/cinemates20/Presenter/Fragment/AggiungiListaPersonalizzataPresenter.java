package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.ListaPersonalizzataDAO;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.AggiungiListaPersonalizzataFragment;

public class AggiungiListaPersonalizzataPresenter {
    AggiungiListaPersonalizzataFragment aggiungiListaPersonalizzataFragment;

    public AggiungiListaPersonalizzataPresenter(AggiungiListaPersonalizzataFragment aggiungiListaPersonalizzataFragment){
        this.aggiungiListaPersonalizzataFragment = aggiungiListaPersonalizzataFragment;
        initializeListener();
    }

    private void initializeListener(){
        Button bCreaListaAggiungiListaPersonalizzata = aggiungiListaPersonalizzataFragment.getbCreaListaAggiungiListaPersonalizzata();
        bCreaListaAggiungiListaPersonalizzata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiListaPersonalizzataFragment.hideKeyboard();
                aggiungiListaPersonalizzata();
            }
        });
    }

    private boolean titoloVuoto(String titolo){
        return titolo.equals("");
    }

    private void onReviewInsertSuccess(){
        aggiungiListaPersonalizzataFragment.mostraAlertDialogOk("LISTA PERSONALIZZATA CREATA"
                ,"Lista creata con successo");
        backToVisualizzaListePersonalizzateFragment();
    }

    private void backToVisualizzaListePersonalizzateFragment(){
        FragmentManager fm = aggiungiListaPersonalizzataFragment.getActivity().getSupportFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    private void aggiungiListaPersonalizzata(){
        String titoloLista = aggiungiListaPersonalizzataFragment.getTitoloLista();
        String descrizioneLista = aggiungiListaPersonalizzataFragment.getDescrizioneLista();
        boolean titoloVuoto = titoloVuoto(titoloLista);

        if(titoloVuoto == true)
            aggiungiListaPersonalizzataFragment.mostraToast("Inserire il titolo della lista");
        else if(titoloLista.length() > 50)
            aggiungiListaPersonalizzataFragment.mostraToast("Superata lunghezza massima titolo (50 caratteri)");
        else if(descrizioneLista.length() > 300)
            aggiungiListaPersonalizzataFragment.mostraToast("Superata lunghezza massima descrizione (300 caratteri)");
        else {
            AggiungiListaPersonalizzataTask aggiungiListaPersonalizzataTask = new AggiungiListaPersonalizzataTask();
            aggiungiListaPersonalizzataTask.execute(titoloLista, descrizioneLista);
        }
    }

    private class AggiungiListaPersonalizzataTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            aggiungiListaPersonalizzataFragment.mostraProgressDialog();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            String username = Utente.getUtenteLoggato().getUsername();
            String titoloLista = strings[0];
            String descrizioneLista = strings[1];
            return listaPersonalizzataDAO.creaListaPers(titoloLista, username, descrizioneLista);
        }

        @Override
        protected void onPostExecute(Boolean listaPersonalizzataInserita) {
            aggiungiListaPersonalizzataFragment.togliProgressDialog();
            if(listaPersonalizzataInserita == false)
                aggiungiListaPersonalizzataFragment.mostraToast("Hai già creato una lista con questo titolo");
            else
                onReviewInsertSuccess();
        }
    }

}