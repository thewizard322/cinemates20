package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.RaccomandaPreferitoFragment;

import java.util.ArrayList;

public class RaccomandaPreferitoPresenter {

    RaccomandaPreferitoFragment raccomandaPreferitoFragment;
    Film filmSelezionato;


    public RaccomandaPreferitoPresenter(RaccomandaPreferitoFragment raccomandaPreferitoFragment, Film film){
        this.raccomandaPreferitoFragment = raccomandaPreferitoFragment;
        this.filmSelezionato = film;
        raccomandaPreferitoFragment.setTvTitoloRaccomandaPreferito(filmSelezionato.getTitolo());
        prelevaAmici();
        initializeListener();
    }

    private void initializeListener(){
        Button bInviaRaccomandaPreferito = raccomandaPreferitoFragment.getbInviaRaccomandaPreferito();
        bInviaRaccomandaPreferito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPrefertoGiaRaccomandato();
            }
        });
    }

    private void riempiSpinnerAmici(ArrayList<String> listAmici){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(raccomandaPreferitoFragment.getActivity()
                , android.R.layout.simple_spinner_dropdown_item, listAmici);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raccomandaPreferitoFragment.setAdapterSpinnerTitoloLista(adapter);
    }

    private void prelevaAmici(){
        PrelevaAmiciTask prelevaAmiciTask = new PrelevaAmiciTask();
        prelevaAmiciTask.execute();
    }

    private void checkPrefertoGiaRaccomandato(){
        String usernameAmico = raccomandaPreferitoFragment.getUsernameAmico();
        if(usernameAmico.equals(""))
            raccomandaPreferitoFragment.mostraToast("Selezionare un amico");
        else {
            CheckPreferitoRaccomandatoTask checkPreferitoRaccomandatoTask = new CheckPreferitoRaccomandatoTask();
            checkPreferitoRaccomandatoTask.execute();
        }
    }

    private void raccomandaFilmPreferito(){
        RaccomandaPreferitoTask raccomandaPreferitoTask = new RaccomandaPreferitoTask();
        raccomandaPreferitoTask.execute();
    }

    private class PrelevaAmiciTask extends AsyncTask<Void,Void, ArrayList<String>>{

        @Override
        protected void onPreExecute() {
            raccomandaPreferitoFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            UtenteDAO utenteDAO = new UtenteDAO();
            return utenteDAO.prelevaUsernameAmici(username);
        }

        @Override
        protected void onPostExecute(ArrayList<String> arrayList) {
            raccomandaPreferitoFragment.togliProgrssDialogCaricamento();
            riempiSpinnerAmici(arrayList);
        }
    }

    private class CheckPreferitoRaccomandatoTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            raccomandaPreferitoFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String usernameMittente = Utente.getUtenteLoggato().getUsername();
            String usernameDestinatario = raccomandaPreferitoFragment.getUsernameAmico();
            int idFilm = filmSelezionato.getId();
            NotificaDAO notificaDAO = new NotificaDAO();
            return notificaDAO.checkPreferitoGiaRaccomandato(usernameMittente,usernameDestinatario,idFilm);
        }

        @Override
        protected void onPostExecute(Boolean filmNonCondiviso) {
            if(filmNonCondiviso == true)
                raccomandaFilmPreferito();
            else{
                raccomandaPreferitoFragment.togliProgrssDialogCaricamento();
                raccomandaPreferitoFragment.mostraAlertDialogOk("ERRORE","Hai già raccomandato questo film" +
                        " a questo amico");
            }
        }
    }

    private class RaccomandaPreferitoTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            raccomandaPreferitoFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String usernameMittente = Utente.getUtenteLoggato().getUsername();
            String usernameDestinatario = raccomandaPreferitoFragment.getUsernameAmico();
            int idFilm = filmSelezionato.getId();
            String titoloFilm = filmSelezionato.getTitolo();
            String dataUscita = filmSelezionato.getDataUscita();
            String posterPath = filmSelezionato.getPathPoster();
            NotificaDAO notificaDAO = new NotificaDAO();
            return notificaDAO.RaccomdandaPreferito(usernameMittente,usernameDestinatario,idFilm,titoloFilm,dataUscita,posterPath);
        }

        @Override
        protected void onPostExecute(Boolean filmRaccomandato) {
            raccomandaPreferitoFragment.togliProgrssDialogCaricamento();
            if(filmRaccomandato == true)
                raccomandaPreferitoFragment.mostraAlertDialogOk("FILM CONDIVISO", "Film condiviso con successo");
            else
                raccomandaPreferitoFragment.mostraAlertDialogOk("ERRORE", "Impossibile raccomandare il film");
        }
    }

}
