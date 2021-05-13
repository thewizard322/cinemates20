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
import com.example.cinemates20.View.Fragment.FilmDaVedereFragment;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.Widgets.AdapterFilmDaVedere;

import java.util.ArrayList;
import java.util.Objects;

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
        filmDaVedereFragment.setAdapterLwDaVedere(adapterFilmDaVedere);
        lwFilmDaVedere.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film filmSelezionato = filmDaVedere.get(position);
                addMostraFilmFragment(filmSelezionato);
            }
        });
    }

    private void addMostraFilmFragment(Film filmSelezionato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = filmDaVedereFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
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
            filmDaVedereFragment.aggiornaLwPreferiti(adapterFilmDaVedere);
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
                filmDaVedereFragment.aggiornaLwPreferiti(adapterFilmDaVedere);
            }
            else if(flag==false) {
                filmDaVedereFragment.mostraAlertDialogOk("Azione fallita","Eliminazione fallita");
            }
        }

    }
}