package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.DAO.RecensioneDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Recensione;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AggiungiAListaPersonalizzataFragment;
import com.example.cinemates20.View.Fragment.InserisciRecensioneFragment;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.Widgets.AdapterMostraFilm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;

public class MostraFilmPresenter {

    private MostraFilmFragment mostraFilmFragment;
    private ArrayList<Recensione> arrayRecensione = new ArrayList<>();
    private Film filmSelezionato;
    private AdapterMostraFilm adapterMostraFilm;

    public MostraFilmPresenter(MostraFilmFragment mostraFilmFragment, Film filmSelezionato){
        this.mostraFilmFragment = mostraFilmFragment;
        this.filmSelezionato = filmSelezionato;
        initializeFilm();
    }

    private void initializeFilm(){
        PrelevaDatiFilmTask prelevaDatiFilmTask = new PrelevaDatiFilmTask();
        prelevaDatiFilmTask.execute();
    }

    private void initializeListView(){
        ListView lwRicercaFilm = mostraFilmFragment.getLwMostraFilm();
        adapterMostraFilm = new AdapterMostraFilm(Objects.requireNonNull(mostraFilmFragment.getContext()),mostraFilmFragment
                ,this,arrayRecensione,filmSelezionato);
        lwRicercaFilm.setAdapter(adapterMostraFilm);
    }

    public void aggiungiAiPreferiti(){
        AggiungiAiPreferitiTask aggiungiAiPreferitiTask = new AggiungiAiPreferitiTask();
        aggiungiAiPreferitiTask.execute();
    }

    public void aggiungiAiFilmDaVedere(){
        AggiungiAiFilmDaVedereTask aggiungiAiPreferitiTask = new AggiungiAiFilmDaVedereTask();
        aggiungiAiPreferitiTask.execute();
    }

    public void replaceAggiungiAListaPersFragment(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new AggiungiAListaPersonalizzataFragment();
        fg.setArguments(bundle);
        FragmentManager fm = mostraFilmFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    public void inserisciRecensione(){
        boolean checkRecensione = checkRecensioneGiaInserita();
        if(checkRecensione == false)
            mostraFilmFragment.mostraAlertDialogOk("ERRORE"
                    , "Hai già inserito una recensione per questo film");
        else
            replaceInserisciRecensioneFragment();
    }

    public void segnalaRecensione(int idRecensione){
        String username = Utente.getUtenteLoggato().getUsername();
        SegnalaRecensioneTask segnalaRecensioneTask = new SegnalaRecensioneTask(username,idRecensione);
        segnalaRecensioneTask.execute();
    }

    public boolean checkRecensioneGiaInserita() {
        Utente utenteLoggato = Utente.getUtenteLoggato();
        for (Recensione recensione : arrayRecensione) {
            if (recensione.getUsername().equals(utenteLoggato.getUsername()))
                return false;
        }
        return true;
    }

    private void replaceInserisciRecensioneFragment(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new InserisciRecensioneFragment();
        fg.setArguments(bundle);
        FragmentManager fm = mostraFilmFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private class PrelevaRecensioniTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... aVoid) {
            RecensioneDAO recensioneDAO = new RecensioneDAO();
            arrayRecensione = recensioneDAO.prelevaRecensioniFilm(filmSelezionato.getId());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            mostraFilmFragment.togliProgrssDialogCaricamento();
            initializeListView();
        }
    }

    private class PrelevaDatiFilmTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            mostraFilmFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... aVoid) {
            TmdbApi tmdbApi = new TmdbApi("2bc3bb8279aa7bcc7bd18d60857dc82a");
            MovieDb moviedB = tmdbApi.getMovies().getMovie(filmSelezionato.getId(),"it"
                    , TmdbMovies.MovieMethod.credits);
            List<PersonCast> cast = moviedB.getCast();
            List<String> attori = new ArrayList<>();
            List<String> generi = new ArrayList<>();
            String trama = moviedB.getOverview();
            String regista = null;
            for(int i=0; i<4; i++) {
                if (i < cast.size())
                    attori.add(cast.get(i).getName());
            }
            for(PersonCrew personCrew : moviedB.getCrew()){
                if(personCrew.getJob().equals("Director"))
                    regista = personCrew.getName();
            }
            for(Genre genre : moviedB.getGenres())
                generi.add(genre.getName());
            filmSelezionato.setAttori(attori);
            filmSelezionato.setGeneri(generi);
            filmSelezionato.setRegista(regista);
            filmSelezionato.setTrama(trama);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PrelevaRecensioniTask prelevaRecensioniTask = new PrelevaRecensioniTask();
            prelevaRecensioniTask.execute();
        }
    }

    private class AggiungiAiPreferitiTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            mostraFilmFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            FilmDAO filmDAO = new FilmDAO();
            boolean filmAggiunto = filmDAO.aggiungiAiPreferiti(username,filmSelezionato);
            return filmAggiunto;
        }

        @Override
        protected void onPostExecute(Boolean filmAggiunto) {
            mostraFilmFragment.togliProgrssDialogCaricamento();
            if(filmAggiunto==false){
                mostraFilmFragment.mostraAlertDialogOk("ERRORE","Hai già questo film tra i preferiti");
            }
            else
                mostraFilmFragment.mostraAlertDialogOk("FILM AGGIUNTO","Film aggiunto ai preferiti");
        }
    }

    private class AggiungiAiFilmDaVedereTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            mostraFilmFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            FilmDAO filmDAO = new FilmDAO();
            boolean filmAggiunto = filmDAO.aggiungiAiFilmDaVedere(username,filmSelezionato);
            return filmAggiunto;
        }

        @Override
        protected void onPostExecute(Boolean filmAggiunto) {
            mostraFilmFragment.togliProgrssDialogCaricamento();
            if(filmAggiunto==false){
                mostraFilmFragment.mostraAlertDialogOk("ERRORE","Hai già questo film da vedere");
            }
            else
                mostraFilmFragment.mostraAlertDialogOk("FILM AGGIUNTO","Film da vedere aggiunto");
        }
    }

    private class SegnalaRecensioneTask extends AsyncTask<Void,Void,Boolean>{

        private String username;
        private int idRecensione;

        public SegnalaRecensioneTask(String username, int idRecensione){
            this.username = username;
            this.idRecensione = idRecensione;
        }

        @Override
        protected void onPreExecute() {
            mostraFilmFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            RecensioneDAO filmDAO = new RecensioneDAO();
            boolean segnalazioneAvvenuta = filmDAO.segnalaRecensione(username,idRecensione);
            return segnalazioneAvvenuta;
        }

        @Override
        protected void onPostExecute(Boolean segnalazioneAvvenuta) {
            mostraFilmFragment.togliProgrssDialogCaricamento();
            if(segnalazioneAvvenuta==false){
                mostraFilmFragment.mostraAlertDialogOk("ERRORE","Hai già segnalato questa recensione " +
                        "e un amministratore ancora non ha preso una decisione");
            }
            else
                mostraFilmFragment.mostraAlertDialogOk("SEGNALAZIONE INVIATA","Recensione segnalata " +
                        "con successo");
        }
    }

}
