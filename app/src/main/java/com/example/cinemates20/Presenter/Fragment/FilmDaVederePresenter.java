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
        prelevaFilmDaVedere();
    }

    private void initializeListView() {
        ListView lwFilmDaVedere = filmDaVedereFragment.getLwFilmDaVedere();
        adapterFilmDaVedere = new AdapterFilmDaVedere(Objects.requireNonNull(filmDaVedereFragment.getContext()), filmDaVedereFragment, filmDaVedere,this);
        lwFilmDaVedere.setAdapter((ListAdapter) adapterFilmDaVedere);
    }

    private void riempiListView(){
        adapterFilmDaVedere.notifyDataSetChanged();
    }

    private void prelevaFilmDaVedere(){
        PrelievoFilmDaVedereTask prelievoFilmDaVedereTask=new PrelievoFilmDaVedereTask();
        prelievoFilmDaVedereTask.execute();
    }

    public void rimuoviFilmDaVedere(int id_film){
        RimozioneFilmDaVedereTask rimozioneFilmDaVedereTask=new RimozioneFilmDaVedereTask();
        rimozioneFilmDaVedereTask.execute(id_film);
    }

    private class PrelievoFilmDaVedereTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            filmDaVedereFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            FilmDAO filmDAO = new FilmDAO();
            filmDaVedere.addAll(filmDAO.prelevaFilmDaVedere(username));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            filmDaVedereFragment.getLwFilmDaVedere().setEmptyView(filmDaVedereFragment.getTvEmptyFilmDaVedere());
            riempiListView();
            filmDaVedereFragment.togliProgrssDialogCaricamento();
        }
    }

    private class RimozioneFilmDaVedereTask extends AsyncTask<Integer,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            filmDaVedereFragment.mostraProgressDialogCaricamento();
        }

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
        protected void onPostExecute(Boolean flag) {
            filmDaVedereFragment.togliProgrssDialogCaricamento();
            if (flag==true) {
                filmDaVedereFragment.mostraAlertDialogOk("Azione completata", "Film eliminato con successo");
                adapterFilmDaVedere.notifyDataSetChanged();
            }
            else if(flag==false) {
                filmDaVedereFragment.mostraAlertDialogOk("Azione fallita","Eliminazione fallita");
            }
        }

    }
}