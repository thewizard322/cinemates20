package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.View.Fragment.PreferitiFragment;
import com.example.cinemates20.View.Fragment.RaccomandaPreferitoFragment;
import com.example.cinemates20.Widgets.AdapterPreferiti;

import java.util.ArrayList;
import java.util.Objects;

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
        ListView lwPreferiti = preferitiFragment.getLwPreferiti();
        adapterPreferiti = new AdapterPreferiti(Objects.requireNonNull(preferitiFragment.getContext()), preferitiFragment, filmPreferiti,this);
        preferitiFragment.setAdapterLwPreferiti(adapterPreferiti);
        lwPreferiti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film filmSelezionato = filmPreferiti.get(position);
                addMostraFilmFragment(filmSelezionato);
            }
        });
    }

    private void prelevaPreferiti(){
        PrelievoPreferitiTask prelievoPreferitiTask=new PrelievoPreferitiTask();
        prelievoPreferitiTask.execute();
    }

    public void rimuoviDaPreferiti(int id_film){
        RimozionePreferitiTask rimozionePreferitiTask=new RimozionePreferitiTask();
        rimozionePreferitiTask.execute(id_film);
    }

    private void addMostraFilmFragment(Film filmSelezionato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = preferitiFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    public void addRaccomandaPreferitoFragment(Film filmSelezionato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new RaccomandaPreferitoFragment();
        fg.setArguments(bundle);
        FragmentManager fm = preferitiFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
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
            preferitiFragment.togliProgressDialogCaricamento();
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
            preferitiFragment.togliProgressDialogCaricamento();
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