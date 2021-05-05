package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cinemates20.DAO.ListaPersonalizzataDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.ListaPersonalizzata;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.AggiungiAListaPersonalizzataFragment;

import java.util.ArrayList;

public class AggiungiAListaPersonalizzataPresenter {

    AggiungiAListaPersonalizzataFragment aggiungiAListaPersonalizzataFragment;
    Film filmSelezionato;

    public AggiungiAListaPersonalizzataPresenter(AggiungiAListaPersonalizzataFragment aggiungiAListaPersonalizzataFragment,
                                                 Film filmSelezionato){
        this.aggiungiAListaPersonalizzataFragment = aggiungiAListaPersonalizzataFragment;
        this.filmSelezionato = filmSelezionato;
        prelevaListePersonalizzate();
        initializeListener();
    }

    private void initializeListener(){
        Button bAggiungiFilmAListaPers = aggiungiAListaPersonalizzataFragment.getbAggiungiFilmAListaPers();
        bAggiungiFilmAListaPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void prelevaListePersonalizzate(){
        PrelevaListeTask prelevaListeTask = new PrelevaListeTask();
        prelevaListeTask.execute(Utente.getUtenteLoggato().getUsername());
    }

    private void riempiSpinnerListe(ArrayList<ListaPersonalizzata> listePers){
        ArrayList<String> arrayList = new ArrayList<>();
        for(ListaPersonalizzata listaPersonalizzata : listePers){
            arrayList.add(listaPersonalizzata.getTitolo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(aggiungiAListaPersonalizzataFragment.getActivity()
                , android.R.layout.simple_spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTitoloListaPersAggiungiAListaPers = aggiungiAListaPersonalizzataFragment
                .getSpinnerTitoloListaPersAggiungiAListaPers();
        spinnerTitoloListaPersAggiungiAListaPers.setAdapter(adapter);

        spinnerTitoloListaPersAggiungiAListaPers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aggiungiAListaPersonalizzataFragment.setTextDescrizione(listePers.get(position).getDescrizione());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private class PrelevaListeTask extends AsyncTask<String,Void, ArrayList<ListaPersonalizzata>>{
        @Override
        protected ArrayList<ListaPersonalizzata> doInBackground(String... strings) {
            String username = strings[0];
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.prelevaListePersonalizzate(username);
        }

        @Override
        protected void onPostExecute(ArrayList<ListaPersonalizzata> listaPersonalizzatas) {
            riempiSpinnerListe(listaPersonalizzatas);
        }
    }

}
