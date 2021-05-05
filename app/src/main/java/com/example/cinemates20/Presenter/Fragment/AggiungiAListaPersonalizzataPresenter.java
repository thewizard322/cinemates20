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
                                                 Film filmSelezionato) {
        this.aggiungiAListaPersonalizzataFragment = aggiungiAListaPersonalizzataFragment;
        this.filmSelezionato = filmSelezionato;
        prelevaListePersonalizzate();
        initializeListener();
    }

    private void initializeListener() {
        Button bAggiungiFilmAListaPers = aggiungiAListaPersonalizzataFragment.getbAggiungiFilmAListaPers();
        bAggiungiFilmAListaPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiFilmAListaPers();
            }
        });
    }

    private void prelevaListePersonalizzate() {
        PrelevaListeTask prelevaListeTask = new PrelevaListeTask();
        prelevaListeTask.execute();
    }

    private void riempiSpinnerListe(ArrayList<ListaPersonalizzata> listePers) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (ListaPersonalizzata listaPersonalizzata : listePers) {
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

    private void aggiungiFilmAListaPers() {
        AggiungiFilmAListaTask aggiungiFilmAListaTask = new AggiungiFilmAListaTask();
        aggiungiFilmAListaTask.execute();
    }

    private class PrelevaListeTask extends AsyncTask<Void, Void, ArrayList<ListaPersonalizzata>> {
        @Override
        protected void onPreExecute() {
            aggiungiAListaPersonalizzataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ArrayList<ListaPersonalizzata> doInBackground(Void... aVoid) {
            String username = Utente.getUtenteLoggato().getUsername();
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.prelevaListePersonalizzate(username);
        }

        @Override
        protected void onPostExecute(ArrayList<ListaPersonalizzata> listaPersonalizzatas) {
            aggiungiAListaPersonalizzataFragment.togliProgrssDialogCaricamento();
            riempiSpinnerListe(listaPersonalizzatas);
        }
    }

    private class AggiungiFilmAListaTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            aggiungiAListaPersonalizzataFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(Void... aVoid) {
            String username = Utente.getUtenteLoggato().getUsername();
            String titoloLista = aggiungiAListaPersonalizzataFragment.getSpinnerTitoloListaPersAggiungiAListaPers()
                    .getSelectedItem().toString();
            int idFilm = filmSelezionato.getId();
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.aggiungiFilmAListaPers(username,titoloLista,idFilm);
        }

        @Override
        protected void onPostExecute(Boolean filmAggiunto) {
            aggiungiAListaPersonalizzataFragment.togliProgrssDialogCaricamento();
            if(filmAggiunto)
                aggiungiAListaPersonalizzataFragment.mostraAlertDialogOk("FILM AGGIUNTO"
                        ,"Film aggiunto alla lista");
            else
                aggiungiAListaPersonalizzataFragment.mostraAlertDialogOk("ERRORE"
                        ,"Hai già questo film nella lista selezionata");
        }
    }

}
