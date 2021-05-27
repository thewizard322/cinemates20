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
import com.example.cinemates20.DAO.ListaPersonalizzataDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.ListaPersonalizzata;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.View.Fragment.VisualizzaListaPersonalizzataRaccomandataFragment;
import com.example.cinemates20.Widgets.AdapterVisualizzaListaPersonalizzataRaccomandata;

import java.util.ArrayList;
import java.util.Objects;

public class VisualizzaListaPersonalizzataRaccomandataPresenter {
    private AdapterVisualizzaListaPersonalizzataRaccomandata adapterVisualizzaListaPersonalizzataRaccomandata;
    private ArrayList<Film> filmListaPersonalizzata = new ArrayList<>();
    private VisualizzaListaPersonalizzataRaccomandataFragment visualizzaListaPersonalizzataRaccomandataFragment;
    private String titoloListaNotificaSelezionata;
    private String usernameMittenteListaNotificaSelezionata;

    public VisualizzaListaPersonalizzataRaccomandataPresenter(VisualizzaListaPersonalizzataRaccomandataFragment visualizzaListaPersonalizzataRaccomandataFragment, String titoloLista, String usernameMittente){
        this.visualizzaListaPersonalizzataRaccomandataFragment = visualizzaListaPersonalizzataRaccomandataFragment;
        this.titoloListaNotificaSelezionata = titoloLista;
        this.usernameMittenteListaNotificaSelezionata = usernameMittente;
        initializeListView();
        prelevaListaPersonalizzata();
    }

    private void initializeListView() {
        ListView lvVisualizzaListePersonalizzate = visualizzaListaPersonalizzataRaccomandataFragment.getLvVisualizzaListaPersonalizzataRaccomandata();
        adapterVisualizzaListaPersonalizzataRaccomandata = new AdapterVisualizzaListaPersonalizzataRaccomandata(Objects.requireNonNull(visualizzaListaPersonalizzataRaccomandataFragment.getContext()), visualizzaListaPersonalizzataRaccomandataFragment, filmListaPersonalizzata, this);
        visualizzaListaPersonalizzataRaccomandataFragment.setAdapterLvAdapterVisualizzaListaPersonalizzataRaccomandata(adapterVisualizzaListaPersonalizzataRaccomandata);
        lvVisualizzaListePersonalizzate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film filmSelezionato = filmListaPersonalizzata.get(position);
                addMostraFilmFragment(filmSelezionato);
            }
        });
    }

    private void addMostraFilmFragment(Film filmSelezionato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaListaPersonalizzataRaccomandataFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void prelevaListaPersonalizzata(){
        PrelevaDescrizioneListaTask prelevaListaTask = new PrelevaDescrizioneListaTask();
        prelevaListaTask.execute();
    }

    private class PrelevaDescrizioneListaTask extends AsyncTask<Void, Void, ListaPersonalizzata> {
        @Override
        protected void onPreExecute() {
            visualizzaListaPersonalizzataRaccomandataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ListaPersonalizzata doInBackground(Void... aVoid) {
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.prelevaDescrizioneListaPersonalizzata(usernameMittenteListaNotificaSelezionata, titoloListaNotificaSelezionata);
        }

        @Override
        protected void onPostExecute(ListaPersonalizzata listaPers) {
            visualizzaListaPersonalizzataRaccomandataFragment.togliProgressDialogCaricamento();
            visualizzaListaPersonalizzataRaccomandataFragment.setTextTitoloLista(titoloListaNotificaSelezionata);
            visualizzaListaPersonalizzataRaccomandataFragment.setTextDescrizione(listaPers.getDescrizione());
            PrelievoFilmListePersonalizzateTask prelievoFilmTask = new PrelievoFilmListePersonalizzateTask();
            prelievoFilmTask.execute();
        }
    }

    private class PrelievoFilmListePersonalizzateTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            filmListaPersonalizzata.clear();
            visualizzaListaPersonalizzataRaccomandataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String username = usernameMittenteListaNotificaSelezionata;
            String titoloLista = titoloListaNotificaSelezionata;
            FilmDAO filmDAO = new FilmDAO();
            filmListaPersonalizzata.addAll(filmDAO.prelevafilmListaPersonalizzata(username, titoloLista));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            visualizzaListaPersonalizzataRaccomandataFragment.getLvVisualizzaListaPersonalizzataRaccomandata().setEmptyView(visualizzaListaPersonalizzataRaccomandataFragment.getTvEmptyListePersonalizzate());
            visualizzaListaPersonalizzataRaccomandataFragment.aggiornaLvVisualizzaListePersonalizzate(adapterVisualizzaListaPersonalizzataRaccomandata);
            visualizzaListaPersonalizzataRaccomandataFragment.togliProgressDialogCaricamento();
        }
    }

}
