package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.PreferitiFragment;
import com.example.cinemates20.Widgets.AdapterPreferiti;

import java.util.ArrayList;
import java.util.Objects;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

public class PreferitiPresenter {
    private ArrayList <Film> filmPreferiti=new ArrayList<Film>();
    AdapterPreferiti adapterPreferiti;
    private PreferitiFragment preferitiFragment;

    public PreferitiPresenter(PreferitiFragment preferitiFragment) {
        this.preferitiFragment = preferitiFragment;
        initializeListView();
        PrelievoIdTask prelievoIdTask = new PrelievoIdTask();
        prelievoIdTask.execute();
    }

    private void initializeListView() {
        ListView lwPreferiti = preferitiFragment.getLwPreferiti();
        adapterPreferiti = new AdapterPreferiti(Objects.requireNonNull(preferitiFragment.getContext()), preferitiFragment, filmPreferiti,this);
        lwPreferiti.setAdapter((ListAdapter) adapterPreferiti);
    }

    private void riempiListView(){
        adapterPreferiti.notifyDataSetChanged();
    }

    private void prelevaPreferiti(ArrayList<Integer> listId){
        PrelievoPreferitiTask prelievoPreferitiTask=new PrelievoPreferitiTask();
        prelievoPreferitiTask.execute(listId);
    }

    public void rimuoviDaPreferiti(int id_film){
        RimozionePreferitiTask rimozionePreferitiTask=new RimozionePreferitiTask();
        rimozionePreferitiTask.execute(id_film);
    }

     private class PrelievoIdTask extends AsyncTask<Void,Void,ArrayList<Integer>>{

        @Override
        protected void onPreExecute() {
            preferitiFragment.getProgressDialogRicercaInCorso().show();
        }

        @Override
        protected ArrayList<Integer> doInBackground(Void... voids){
            FilmDAO filmDAO = new FilmDAO();
            ArrayList<Integer> list=filmDAO.preleva_id_preferiti(Utente.getUtenteLoggato().getUsername());
             return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> list) {
            prelevaPreferiti(list);
        }
    }

    private class PrelievoPreferitiTask extends AsyncTask<ArrayList<Integer>,Void,Void> {
        @Override
        protected Void doInBackground(ArrayList<Integer>... arrayLists) {
            TmdbApi tmdbApi = new TmdbApi("2bc3bb8279aa7bcc7bd18d60857dc82a");
            for(Integer id_film:arrayLists[0]) {
                MovieDb moviedB = tmdbApi.getMovies().getMovie(id_film,"it");
                Film film = new Film(id_film,moviedB.getTitle()  ,moviedB.getReleaseDate(),moviedB.getPosterPath());
                filmPreferiti.add(film);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            preferitiFragment.getLwPreferiti().setEmptyView(preferitiFragment.getTvEmptyPreferiti());
            riempiListView();
            preferitiFragment.getProgressDialogRicercaInCorso().dismiss();
        }
    }

    private class RimozionePreferitiTask extends AsyncTask<Integer,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            int id_film=integers[0];
            String username=Utente.getUtenteLoggato().getUsername();
            FilmDAO filmDao=new FilmDAO();
            boolean flag=filmDao.eliminaDaPreferiti(username,id_film);
            if (flag==true) {
                Film filmDaEliminare = null;
                for (Film film : filmPreferiti) {
                    if (film.getId() == id_film)
                        filmDaEliminare = film;
                }
                if(filmDaEliminare!=null){
                    filmPreferiti.remove(filmDaEliminare);
                }
            }
            return flag;
        }

        @Override
        protected void onPreExecute() {
            preferitiFragment.getProgressDialogRicercaInCorso().show();
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            if (flag==true){
                adapterPreferiti.notifyDataSetChanged();
            }
            preferitiFragment.getProgressDialogRicercaInCorso().dismiss();
        }
    }
}