package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.FilmDaVedereFragment;
import com.example.cinemates20.Widgets.AdapterFilmDaVedere;

import java.util.ArrayList;
import java.util.Objects;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

public class FilmDaVederePresenter {
    private ArrayList <Film> filmDaVedere=new ArrayList<Film>();
    AdapterFilmDaVedere adapterFilmDaVedere;
    private FilmDaVedereFragment filmDaVedereFragment;

    public FilmDaVederePresenter(FilmDaVedereFragment filmDaVedereFragment) {
        this.filmDaVedereFragment = filmDaVedereFragment;
        initializeListView();
        PrelievoIdTask prelievoIdTask = new PrelievoIdTask();
        prelievoIdTask.execute();
    }

    private void initializeListView() {
        ListView lwFilmDaVedere = filmDaVedereFragment.getLwFilmDaVedere();
        adapterFilmDaVedere = new AdapterFilmDaVedere(Objects.requireNonNull(filmDaVedereFragment.getContext()), filmDaVedereFragment, filmDaVedere,this);
        lwFilmDaVedere.setAdapter((ListAdapter) adapterFilmDaVedere);
    }

    private void riempiListView(){
        adapterFilmDaVedere.notifyDataSetChanged();
    }

    private void prelevaFilmDaVedere(ArrayList<Integer> listId){
        PrelievoFilmDaVedereTask prelievoFilmDaVedereTask=new PrelievoFilmDaVedereTask();
        prelievoFilmDaVedereTask.execute(listId);
    }

    public void rimuoviFilmDaVedere(int id_film){
        RimozioneFilmDaVedereTask rimozioneFilmDaVedereTask=new RimozioneFilmDaVedereTask();
        rimozioneFilmDaVedereTask.execute(id_film);
    }

    private class PrelievoIdTask extends AsyncTask<Void,Void,ArrayList<Integer>>{

        @Override
        protected void onPreExecute() {
            filmDaVedereFragment.getProgressDialogRicercaInCorso().show();
        }

        @Override
        protected ArrayList<Integer> doInBackground(Void... voids){
            FilmDAO filmDAO = new FilmDAO();
            ArrayList<Integer> list=filmDAO.preleva_id_film_da_vedere(Utente.getUtenteLoggato().getUsername());
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> list) {
            prelevaFilmDaVedere(list);
        }
    }

    private class PrelievoFilmDaVedereTask extends AsyncTask<ArrayList<Integer>,Void,Void> {
        @Override
        protected Void doInBackground(ArrayList<Integer>... arrayLists) {
            TmdbApi tmdbApi = new TmdbApi("2bc3bb8279aa7bcc7bd18d60857dc82a");
            for(Integer id_film:arrayLists[0]) {
                MovieDb moviedB = tmdbApi.getMovies().getMovie(id_film,"it");
                Film film = new Film(id_film,moviedB.getTitle()  ,moviedB.getReleaseDate(),moviedB.getPosterPath());
                filmDaVedere.add(film);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            filmDaVedereFragment.getLwFilmDaVedere().setEmptyView(filmDaVedereFragment.getTvEmptyFilmDaVedere());
            riempiListView();
            filmDaVedereFragment.getProgressDialogRicercaInCorso().dismiss();
        }
    }

    private class RimozioneFilmDaVedereTask extends AsyncTask<Integer,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            int id_film=integers[0];
            String username=Utente.getUtenteLoggato().getUsername();
            FilmDAO filmDao=new FilmDAO();
            boolean flag=filmDao.eliminaFilmDaVedere(username,id_film);
            if (flag==true) {
                Film filmDaEliminare = null;
                for (Film film : filmDaVedere) {
                    if (film.getId() == id_film)
                        filmDaEliminare = film;
                }
                if(filmDaEliminare!=null){
                    filmDaVedere.remove(filmDaEliminare);
                }
            }
            return flag;
        }

        @Override
        protected void onPreExecute() {
            filmDaVedereFragment.getProgressDialogRicercaInCorso().show();
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            if (flag==true){
                adapterFilmDaVedere.notifyDataSetChanged();
            }
            filmDaVedereFragment.getProgressDialogRicercaInCorso().dismiss();
        }
    }
}