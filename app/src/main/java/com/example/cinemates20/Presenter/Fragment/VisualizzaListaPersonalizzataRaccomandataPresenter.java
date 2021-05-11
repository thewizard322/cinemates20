package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.DAO.ListaPersonalizzataDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.ListaPersonalizzata;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AggiungiListaPersonalizzataFragment;
import com.example.cinemates20.View.Fragment.RaccomandaListaPersonalizzataFragment;
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
        initializeListener();
        initializeListView();
        prelevaListePersonalizzate();
    }

    private void initializeListView() {
        adapterVisualizzaListaPersonalizzataRaccomandata = new AdapterVisualizzaListaPersonalizzataRaccomandata(Objects.requireNonNull(visualizzaListaPersonalizzataRaccomandataFragment.getContext()), visualizzaListaPersonalizzataRaccomandataFragment, filmListaPersonalizzata, this);
        visualizzaListaPersonalizzataRaccomandataFragment.setAdapterLvAdapterVisualizzaListaPersonalizzataRaccomandata(adapterVisualizzaListaPersonalizzataRaccomandata);
    }

    /*
    private void replaceAggiungiListaPersonalizzataFragment(){
        Fragment fg = new AggiungiListaPersonalizzataFragment();
        FragmentManager fm = visualizzaListaPersonalizzataRaccomandataFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }
     */

    private void initializeListener(){
        ImageButton bAggiungiVisualizzaListePersonalizzate = visualizzaListaPersonalizzataRaccomandataFragment.getbAggiungiVisualizzaListePersonalizzate();
        Button btRaccomandaListaPersonalizzata = visualizzaListaPersonalizzataRaccomandataFragment.getBtRaccomandaListaPersonalizzata();
        bAggiungiVisualizzaListePersonalizzate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replaceAggiungiListaPersonalizzataFragment();
            }
        });
        btRaccomandaListaPersonalizzata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRaccomandaLstaPersonalizzataFragment();
            }
        });
    }

    private void replaceRaccomandaLstaPersonalizzataFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("titoloLista", visualizzaListaPersonalizzataRaccomandataFragment.getTitoloLista());
        Fragment fg = new RaccomandaListaPersonalizzataFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaListaPersonalizzataRaccomandataFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void prelevaListePersonalizzate(){
        VisualizzaListaPersonalizzataRaccomandataPresenter.PrelevaListeTask prelevaListeTask = new VisualizzaListaPersonalizzataRaccomandataPresenter.PrelevaListeTask();
        prelevaListeTask.execute();
    }

    private class PrelevaListeTask extends AsyncTask<Void, Void, ArrayList<ListaPersonalizzata>> {
        @Override
        protected void onPreExecute() {
            visualizzaListaPersonalizzataRaccomandataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ArrayList<ListaPersonalizzata> doInBackground(Void... aVoid) {
            String username = Utente.getUtenteLoggato().getUsername();
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.prelevaListePersonalizzate(username);
        }

        @Override
        protected void onPostExecute(ArrayList<ListaPersonalizzata> listaPers) {
            visualizzaListaPersonalizzataRaccomandataFragment.togliProgressDialogCaricamento();
            visualizzaListaPersonalizzataRaccomandataFragment.setTextTitoloLista(titoloListaNotificaSelezionata);
            visualizzaListaPersonalizzataRaccomandataFragment.setTextDescrizione(usernameMittenteListaNotificaSelezionata);
            VisualizzaListaPersonalizzataRaccomandataPresenter.PrelievoFilmListePersonalizzateTask prelievoFilmTask = new VisualizzaListaPersonalizzataRaccomandataPresenter.PrelievoFilmListePersonalizzateTask();
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
