package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AggiungiAListaPersonalizzataFragment;
import com.example.cinemates20.View.Fragment.PreferitiFragment;
import com.example.cinemates20.View.Fragment.RaccomandaPreferitoFragment;
import com.example.cinemates20.Widgets.AdapterPreferiti;

import java.util.ArrayList;
import java.util.Objects;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

public class PreferitiPresenter {
    private ArrayList <Film> filmPreferiti=new ArrayList<Film>();
    private AdapterPreferiti adapterPreferiti;
    private PreferitiFragment preferitiFragment;

    public PreferitiPresenter(PreferitiFragment preferitiFragment) {
        this.preferitiFragment = preferitiFragment;
        initializeListView();
        prelevaPreferiti();
    }

    private void initializeListView() {
        adapterPreferiti = new AdapterPreferiti(Objects.requireNonNull(preferitiFragment.getContext()), preferitiFragment, filmPreferiti,this);
        preferitiFragment.setAdapterLwPreferiti(adapterPreferiti);
    }

    private void prelevaPreferiti(){
        PrelievoPreferitiTask prelievoPreferitiTask=new PrelievoPreferitiTask();
        prelievoPreferitiTask.execute();
    }

    public void rimuoviDaPreferiti(int id_film){
        RimozionePreferitiTask rimozionePreferitiTask=new RimozionePreferitiTask();
        rimozionePreferitiTask.execute(id_film);
    }

    public void replaceRaccomandaPreferitoFragment(Film filmSelezionato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new RaccomandaPreferitoFragment();
        fg.setArguments(bundle);
        FragmentManager fm = preferitiFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private class PrelievoPreferitiTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            preferitiFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            FilmDAO filmDAO = new FilmDAO();
            filmPreferiti.addAll(filmDAO.prelevaPreferiti(username));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            preferitiFragment.getLwPreferiti().setEmptyView(preferitiFragment.getTvEmptyPreferiti());
            preferitiFragment.aggiornaLwPreferiti(adapterPreferiti);
            preferitiFragment.togliProgrssDialogCaricamento();
        }
    }

    private class RimozionePreferitiTask extends AsyncTask<Integer,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            preferitiFragment.mostraProgressDialogCaricamento();
        }

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
        protected void onPostExecute(Boolean flag) {
            preferitiFragment.togliProgrssDialogCaricamento();
            if (flag==true) {
                preferitiFragment.mostraAlertDialogOk("Azione completata", "Film eliminato con successo");
                preferitiFragment.aggiornaLwPreferiti(adapterPreferiti);
            }
            else if(flag==false) {
                preferitiFragment.mostraAlertDialogOk("Azione fallita","Eliminazione fallita");
            }
        }
    }
}