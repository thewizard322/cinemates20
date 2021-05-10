package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
import com.example.cinemates20.View.Fragment.VisualizzaListePersonalizzateFragment;
import com.example.cinemates20.Widgets.AdapterVisualizzaListePersonalizzate;

import java.util.ArrayList;
import java.util.Objects;


public class VisualizzaListePersonalizzatePresenter {
    private AdapterVisualizzaListePersonalizzate adapterVisualizzaListePersonalizzate;
    private ArrayList<Film> filmListaPersonalizzata = new ArrayList<>();
    private VisualizzaListePersonalizzateFragment visualizzaListePersonalizzateFragment;

    public VisualizzaListePersonalizzatePresenter(VisualizzaListePersonalizzateFragment visualizzaListePersonalizzateFragment){
        this.visualizzaListePersonalizzateFragment = visualizzaListePersonalizzateFragment;
        initializeListener();
        initializeListView();
        prelevaListePersonalizzate();
    }

    private void initializeListView() {
        adapterVisualizzaListePersonalizzate = new AdapterVisualizzaListePersonalizzate(Objects.requireNonNull(visualizzaListePersonalizzateFragment.getContext()), visualizzaListePersonalizzateFragment, filmListaPersonalizzata, this);
        visualizzaListePersonalizzateFragment.setAdapterLvVisualizzaListePersonalizzate(adapterVisualizzaListePersonalizzate);
    }

    private void replaceAggiungiListaPersonalizzataFragment(){
        Fragment fg = new AggiungiListaPersonalizzataFragment();
        FragmentManager fm = visualizzaListePersonalizzateFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void initializeListener(){
        ImageButton bAggiungiVisualizzaListePersonalizzate = visualizzaListePersonalizzateFragment.getbAggiungiVisualizzaListePersonalizzate();
        bAggiungiVisualizzaListePersonalizzate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceAggiungiListaPersonalizzataFragment();
            }
        });
    }

    private void prelevaListePersonalizzate(){
        PrelevaListeTask prelevaListeTask = new PrelevaListeTask();
        prelevaListeTask.execute();
    }

    private void riempiSpinnerListe(ArrayList<ListaPersonalizzata> listePers) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListaPersonalizzata listaPersonalizzata : listePers) {
            arrayList.add(listaPersonalizzata.getTitolo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(visualizzaListePersonalizzateFragment.getActivity()
                , android.R.layout.simple_spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTitoloVisualizzaListePersonalizzate = visualizzaListePersonalizzateFragment.getSpinnerTitoloVisualizzaListePersonalizzate();
        visualizzaListePersonalizzateFragment.setAdapterSpinnerTitoloLista(adapter);

        spinnerTitoloVisualizzaListePersonalizzate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visualizzaListePersonalizzateFragment.setTextDescrizione(listePers.get(position).getDescrizione());

                PrelievoFilmListePersonalizzateTask prelievoFilmTask = new PrelievoFilmListePersonalizzateTask();
                prelievoFilmTask.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class PrelevaListeTask extends AsyncTask<Void, Void, ArrayList<ListaPersonalizzata>> {
        @Override
        protected void onPreExecute() {
            visualizzaListePersonalizzateFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ArrayList<ListaPersonalizzata> doInBackground(Void... aVoid) {
            String username = Utente.getUtenteLoggato().getUsername();
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.prelevaListePersonalizzate(username);
        }

        @Override
        protected void onPostExecute(ArrayList<ListaPersonalizzata> listaPers) {
            visualizzaListePersonalizzateFragment.togliProgressDialogCaricamento();
            riempiSpinnerListe(listaPers);
        }
    }

    private class PrelievoFilmListePersonalizzateTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            filmListaPersonalizzata.clear();
            visualizzaListePersonalizzateFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            String titoloLista = visualizzaListePersonalizzateFragment.getTitoloLista();
            FilmDAO filmDAO = new FilmDAO();
            filmListaPersonalizzata.addAll(filmDAO.prelevafilmListaPersonalizzata(username, titoloLista));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            visualizzaListePersonalizzateFragment.getLvVisualizzaListePersonalizzate().setEmptyView(visualizzaListePersonalizzateFragment.getTvEmptyListePersonalizzate());
            visualizzaListePersonalizzateFragment.aggiornaLvVisualizzaListePersonalizzate(adapterVisualizzaListePersonalizzate);
            visualizzaListePersonalizzateFragment.togliProgressDialogCaricamento();
        }
    }

}

