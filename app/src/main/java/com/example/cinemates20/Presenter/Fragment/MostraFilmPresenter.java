package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.widget.ListView;

import com.example.cinemates20.DAO.RecensioneDAO;
import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Recensione;
import com.example.cinemates20.Model.Utente;
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
    AdapterMostraFilm adapterMostraFilm;

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
        String username = Utente.getUtenteLoggato().getUsername();
        Integer idFilm = filmSelezionato.getId();
        AggiungiAiPreferitiTask aggiungiAiPreferitiTask = new AggiungiAiPreferitiTask(username,idFilm);
        aggiungiAiPreferitiTask.execute();
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
            initializeListView();
        }
    }

    private class PrelevaDatiFilmTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... aVoid) {
            TmdbApi tmdbApi = new TmdbApi("2bc3bb8279aa7bcc7bd18d60857dc82a");
            MovieDb moviedB = tmdbApi.getMovies().getMovie(filmSelezionato.getId(),"it", TmdbMovies.MovieMethod.credits);
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

        private String username;
        private int idFilm;

        public AggiungiAiPreferitiTask(String username, int idFilm){
            this.username = username;
            this.idFilm = idFilm;
        }

        @Override
        protected void onPreExecute() {
            mostraFilmFragment.getProgressDialogCaricamento().show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            UtenteDAO utenteDAO = new UtenteDAO();
            boolean filmAggiunto = utenteDAO.aggiungiAiPreferiti(username,idFilm);
            return filmAggiunto;
        }

        @Override
        protected void onPostExecute(Boolean filmAggiunto) {
            mostraFilmFragment.getProgressDialogCaricamento().dismiss();
            if(filmAggiunto==false){
                mostraFilmFragment.mostraAlertDialogOk("ERRORE","Hai già questo film tra i preferiti");
            }
            else
                mostraFilmFragment.mostraAlertDialogOk("FILM AGGIUNTO","Film aggiunto ai preferiti");
        }
    }

}
