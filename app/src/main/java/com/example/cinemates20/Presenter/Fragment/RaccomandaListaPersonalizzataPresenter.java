package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.RaccomandaListaPersonalizzataFragment;

import java.util.ArrayList;

public class RaccomandaListaPersonalizzataPresenter {

    private RaccomandaListaPersonalizzataFragment raccomandaListaPersonalizzataFragment;
    private String titoloListaSelezionata;

    public RaccomandaListaPersonalizzataPresenter(RaccomandaListaPersonalizzataFragment raccomandaListaPersonalizzataFragment,
                                        String titoloLista){
        this.raccomandaListaPersonalizzataFragment = raccomandaListaPersonalizzataFragment;
        this.titoloListaSelezionata = titoloLista;
        raccomandaListaPersonalizzataFragment.setTvTitoloRaccomandaPreferito(titoloListaSelezionata);
        prelevaAmici();
        initializeListener();
    }

    private void initializeListener(){
        Button bInviaRaccomandaLista = raccomandaListaPersonalizzataFragment.getbInviaRaccomandaLista();
        bInviaRaccomandaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListaGiaRaccomandata();
            }
        });
    }

    private void prelevaAmici(){
        PrelevaAmiciTask prelevaAmiciTask = new PrelevaAmiciTask();
        prelevaAmiciTask.execute();
    }

    private void riempiSpinnerAmici(ArrayList<String> listAmici){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(raccomandaListaPersonalizzataFragment.getActivity()
                , android.R.layout.simple_spinner_dropdown_item, listAmici);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raccomandaListaPersonalizzataFragment.setAdapterSpinnerTitoloLista(adapter);
    }

    private void checkListaGiaRaccomandata(){
        String usernameAmico = raccomandaListaPersonalizzataFragment.getUsernameAmico();
        if(usernameAmico.equals(""))
            raccomandaListaPersonalizzataFragment.mostraToast("Selezionare un amico");
        else {
            CheckListaRaccomandataTask checkListaRaccomandataTask = new CheckListaRaccomandataTask();
            checkListaRaccomandataTask.execute();
        }
    }

    private void raccomandaLista(){
        RaccomandaListaTask raccomandaListaTask = new RaccomandaListaTask();
        raccomandaListaTask.execute();
    }

    private class PrelevaAmiciTask extends AsyncTask<Void,Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            raccomandaListaPersonalizzataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            UtenteDAO utenteDAO = new UtenteDAO();
            return utenteDAO.prelevaUsernameAmici(username);
        }

        @Override
        protected void onPostExecute(ArrayList<String> arrayList) {
            raccomandaListaPersonalizzataFragment.togliProgrssDialogCaricamento();
            riempiSpinnerAmici(arrayList);
        }
    }

    private class CheckListaRaccomandataTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            raccomandaListaPersonalizzataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String usernameMittente = Utente.getUtenteLoggato().getUsername();
            String usernameDestinatario = raccomandaListaPersonalizzataFragment.getUsernameAmico();
            NotificaDAO notificaDAO = new NotificaDAO();
            return notificaDAO.checkListaGiaRaccomandata(usernameMittente,usernameDestinatario,titoloListaSelezionata);
        }

        @Override
        protected void onPostExecute(Boolean listaNonCondivisa) {
            if(listaNonCondivisa == true)
                raccomandaLista();
            else{
                raccomandaListaPersonalizzataFragment.togliProgrssDialogCaricamento();
                raccomandaListaPersonalizzataFragment.mostraAlertDialogOk("ERRORE","Hai già raccomandato questa lista" +
                        " a questo amico");
            }
        }
    }

    private class RaccomandaListaTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            raccomandaListaPersonalizzataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String usernameMittente = Utente.getUtenteLoggato().getUsername();
            String usernameDestinatario = raccomandaListaPersonalizzataFragment.getUsernameAmico();
            NotificaDAO notificaDAO = new NotificaDAO();
            return notificaDAO.raccomandaLista(usernameMittente,usernameDestinatario,titoloListaSelezionata);
        }

        @Override
        protected void onPostExecute(Boolean listaRaccomandata) {
            raccomandaListaPersonalizzataFragment.togliProgrssDialogCaricamento();
            if(listaRaccomandata == true)
                raccomandaListaPersonalizzataFragment.mostraAlertDialogOk("LISTA CONDIVISA", "Lista condivisa con successo");
            else
                raccomandaListaPersonalizzataFragment.mostraAlertDialogOk("ERRORE", "Impossibile raccomandare la lista");
        }
    }

}
