package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.Widgets.AdapterRicercaFilm;
import com.example.cinemates20.View.Fragment.RicercaFilmFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class RicercaFilmPresenter {

    private AdapterRicercaFilm adapterRicercaFilm;
    private ArrayList<Film> arrayListFilm = new ArrayList<Film>();
    private RicercaFilmFragment ricercaFilmFragment;

    public RicercaFilmPresenter(RicercaFilmFragment ricercaFilmFragment){
        this.ricercaFilmFragment = ricercaFilmFragment;
        initializeListener();
        initializeListView();
    }

    private void initializeListView(){
        ListView lwRicercaFilm = ricercaFilmFragment.getLwRicercaFilm();
        adapterRicercaFilm = new AdapterRicercaFilm(Objects.requireNonNull(ricercaFilmFragment.getContext()),ricercaFilmFragment,arrayListFilm);
        lwRicercaFilm.setAdapter(adapterRicercaFilm);
        lwRicercaFilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceMostraFilmFragment(position);
            }
        });
    }

    private void initializeListener(){
        Button bRicercaFilm = ricercaFilmFragment.getbRicercaFilm();
        bRicercaFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ricercaFilmFragment.hideKeyboard();
                effettuaRicerca();
            }
        });
        EditText etTitoloRicercaFilm = ricercaFilmFragment.getEtTitoloRicercaFilm();
        etTitoloRicercaFilm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    bRicercaFilm.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private void replaceMostraFilmFragment(int i){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", arrayListFilm.get(i));
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = ricercaFilmFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void effettuaRicerca(){
        String titoloInserito = ricercaFilmFragment.getTitolo();
        if (!titoloNonVuoto(titoloInserito))
            ricercaFilmFragment.mostraToast("Inserire un titolo");
        else {
            RicercaTask ricercaTask = new RicercaTask();
            ricercaTask.execute(titoloInserito);
        }
    }

    private void riempiListView(){
        adapterRicercaFilm.notifyDataSetChanged();
    }

    private boolean titoloNonVuoto(String titolo){
        if(titolo.equals(""))
            return false;
        return true;
    }

    private class RicercaTask extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {
            ricercaFilmFragment.getProgressDialogRicercaInCorso().show();
            arrayListFilm.clear();
        }

        @Override
        protected Void doInBackground(String... strings) {
            TmdbApi tmdbApi = new TmdbApi("2bc3bb8279aa7bcc7bd18d60857dc82a");
            MovieResultsPage movieResultsPage = tmdbApi.getSearch().searchMovie(strings[0],null,
                    "it",true,1);
            List<MovieDb> listFilmApi = movieResultsPage.getResults();

            for(MovieDb movieDb : listFilmApi){
                Film film = new Film(movieDb.getId(),movieDb.getTitle(),movieDb.getReleaseDate()
                        ,movieDb.getPosterPath());
                arrayListFilm.add(film);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ricercaFilmFragment.getLwRicercaFilm()
                   .setEmptyView(ricercaFilmFragment.getTvEmptyRicercaFilm());
            riempiListView();
            ricercaFilmFragment.getProgressDialogRicercaInCorso().dismiss();
        }
    }

}
