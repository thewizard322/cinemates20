package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.AggiungiAmicoFragment;
import com.example.cinemates20.Widgets.AdapterAggiungiAmici;

import java.util.ArrayList;
import java.util.Objects;


public class AggiungiAmicoPresenter {

    private AdapterAggiungiAmici adapterAggiungiAmici;
    private ArrayList<String> arrayList = new ArrayList<String>();
    private AggiungiAmicoFragment aggiungiAmicoFragment;

    public AggiungiAmicoPresenter(AggiungiAmicoFragment aggiungiAmicoFragment){
        this.aggiungiAmicoFragment = aggiungiAmicoFragment;
        initializeListView();
        initializeListener();
    }

    private void initializeListView(){
        adapterAggiungiAmici = new AdapterAggiungiAmici(Objects.requireNonNull(aggiungiAmicoFragment.getContext()),aggiungiAmicoFragment,arrayList,this);
        aggiungiAmicoFragment.setAdapterLwRicercaUtente(adapterAggiungiAmici);

    }

    private void initializeListener(){
        Button btRicercaUtente = aggiungiAmicoFragment.getbRicercaUtenti();
        btRicercaUtente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiAmicoFragment.hideKeyboard();
                effettuaRicerca();
            }
        });
        EditText etUsername = aggiungiAmicoFragment.getEtUsername();
        etUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    btRicercaUtente.performClick();
                    return true;
                }
                return false;
            }
        });
    }


    private void effettuaRicerca(){
        String usernameInserita = aggiungiAmicoFragment.getUsername();
        if (!usernameNonVuoto(usernameInserita))
            aggiungiAmicoFragment.mostraToast("Inserire un username");
        else {
            RicercaTask ricercaTask = new RicercaTask();
            ricercaTask.execute(usernameInserita);
        }
    }
    public void inviaRichiestaAmicizia(String username){
        InviaRichiestaAmiciziaTask inviaRichiestaAmiciziaTask=new InviaRichiestaAmiciziaTask();
        inviaRichiestaAmiciziaTask.execute(username);
    }

    private boolean usernameNonVuoto(String titolo){
        if(titolo.equals(""))
            return false;
        return true;
    }

    private class RicercaTask extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {
            aggiungiAmicoFragment.mostraProgressDialogRicercaInCorso();
            arrayList.clear();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String utenteConnesso=Utente.getUtenteLoggato().getUsername();
            String utenteCercato = strings[0];
            UtenteDAO utenteDAO=new UtenteDAO();
            arrayList.addAll(utenteDAO.prelevaUsernameRicerca(utenteCercato,utenteConnesso));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            aggiungiAmicoFragment.getLwUtenti().setEmptyView(aggiungiAmicoFragment.getTvEmptyRicercaUtente());
            aggiungiAmicoFragment.aggiornaLwUtenti(adapterAggiungiAmici);
            aggiungiAmicoFragment.togliProgressDialogRicercaInCorso();
        }
    }

    private class InviaRichiestaAmiciziaTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            aggiungiAmicoFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
           String utenteCollegato=Utente.getUtenteLoggato().getUsername();
           NotificaDAO notificaDao=new NotificaDAO();
           boolean flag=notificaDao.inviaNotificaAmicizia(utenteCollegato, strings[0], "RAR");
           return flag;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            aggiungiAmicoFragment.togliProgressDialogCaricamento();
            if(flag==true) {
                effettuaRicerca();
                aggiungiAmicoFragment.mostraAlertDialogOk("Richiesta di amicizia inviata","Operazione effettuata con successo");
            }
            if(flag==false) {
                aggiungiAmicoFragment.mostraAlertDialogOk("Errore","Richiesta di amicizia non inviata");
            }
        }
    }

}
