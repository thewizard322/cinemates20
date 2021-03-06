package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.FilmDAO;
import com.example.cinemates20.DAO.ListaPersonalizzataDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.ListaPersonalizzata;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.View.Fragment.ValutaListaAmicoFragment;
import com.example.cinemates20.View.Fragment.VisualizzaListePersonalizzateAmiciFragment;
import com.example.cinemates20.Widgets.AdapterVisualizzaListePersonalizzateAmici;

import java.util.ArrayList;
import java.util.Objects;

public class VisualizzaListePersonalizzateAmiciPresenter {

    private AdapterVisualizzaListePersonalizzateAmici adapterVisualizzaListePersonalizzateAmici;
    private ArrayList<Film> filmListaPersonalizzata = new ArrayList<>();
    private VisualizzaListePersonalizzateAmiciFragment visualizzaListePersonalizzateAmiciFragment;
    private String amicoselezionato;

    public VisualizzaListePersonalizzateAmiciPresenter(VisualizzaListePersonalizzateAmiciFragment visualizzaListePersonalizzateAmiciFragment,String amico){
        this.visualizzaListePersonalizzateAmiciFragment = visualizzaListePersonalizzateAmiciFragment;
        this.amicoselezionato=amico;
        initializeListener();
        initializeListView();
        prelevaListePersonalizzate();
    }

    private void initializeListView() {
        ListView lvVisualizzaListePersonalizzateAmico = visualizzaListePersonalizzateAmiciFragment.getLvVisualizzaListePersonalizzateAmico();
        adapterVisualizzaListePersonalizzateAmici = new AdapterVisualizzaListePersonalizzateAmici(Objects.requireNonNull(visualizzaListePersonalizzateAmiciFragment.getContext()), visualizzaListePersonalizzateAmiciFragment, filmListaPersonalizzata, this);
        visualizzaListePersonalizzateAmiciFragment.setAdapterVisualizzaListePersonalizzateAmico(adapterVisualizzaListePersonalizzateAmici);
        lvVisualizzaListePersonalizzateAmico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film filmSelezionato = filmListaPersonalizzata.get(position);
                addMostraFilmFragment(filmSelezionato);
            }
        });
    }

    private void initializeListener(){
        Button btValutaListaPersonalizzataAmico = visualizzaListePersonalizzateAmiciFragment.getBtValutaListaPersonalizzataAmico();
        btValutaListaPersonalizzataAmico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titoloLista = visualizzaListePersonalizzateAmiciFragment.getTitoloLista();
                if(titoloLista!=null && !titoloLista.equals(""))
                    replaceValutaListaAmicoFragment(titoloLista);
                else
                    visualizzaListePersonalizzateAmiciFragment.mostraToast("Selezioare una lista da valutare");
            }
        });
    }

    private void addMostraFilmFragment(Film filmSelezionato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmSelezionato);
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaListePersonalizzateAmiciFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void replaceValutaListaAmicoFragment(String titoloLista){
        Bundle bundle = new Bundle();
        bundle.putString("titoloLista", titoloLista);
        bundle.putString("utenteLista", amicoselezionato);
        Fragment fg = new ValutaListaAmicoFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaListePersonalizzateAmiciFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(visualizzaListePersonalizzateAmiciFragment.getActivity()
                , android.R.layout.simple_spinner_dropdown_item, arrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTitoloVisualizzaListePersonalizzate = visualizzaListePersonalizzateAmiciFragment.getSpinnerTitoloVisualizzaListePersonalizzateAmici();
        visualizzaListePersonalizzateAmiciFragment.setAdapterSpinnerTitoloLista(adapter);

        spinnerTitoloVisualizzaListePersonalizzate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String descrizione = listePers.get(position).getDescrizione();
                if(descrizione!=null && !descrizione.equals("")){
                    visualizzaListePersonalizzateAmiciFragment.setVisibilityTvDescrizione(View.VISIBLE);
                    visualizzaListePersonalizzateAmiciFragment.setVisibilityTvDescrizioneVisualizzaListePersonalizzateAmici(View.VISIBLE);
                    visualizzaListePersonalizzateAmiciFragment.setTextDescrizione(listePers.get(position).getDescrizione());
                }
                else {
                    visualizzaListePersonalizzateAmiciFragment.setVisibilityTvDescrizione(View.GONE);
                    visualizzaListePersonalizzateAmiciFragment.setVisibilityTvDescrizioneVisualizzaListePersonalizzateAmici(View.GONE);
                }

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
            visualizzaListePersonalizzateAmiciFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected ArrayList<ListaPersonalizzata> doInBackground(Void... aVoid) {
            ListaPersonalizzataDAO listaPersonalizzataDAO = new ListaPersonalizzataDAO();
            return listaPersonalizzataDAO.prelevaListePersonalizzate(amicoselezionato);
        }

        @Override
        protected void onPostExecute(ArrayList<ListaPersonalizzata> listaPers) {
            visualizzaListePersonalizzateAmiciFragment.togliProgressDialogCaricamento();
            riempiSpinnerListe(listaPers);
        }
    }

    private class PrelievoFilmListePersonalizzateTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            filmListaPersonalizzata.clear();
            visualizzaListePersonalizzateAmiciFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) { ;
            String titoloLista = visualizzaListePersonalizzateAmiciFragment.getTitoloLista();
            FilmDAO filmDAO = new FilmDAO();
            filmListaPersonalizzata.addAll(filmDAO.prelevafilmListaPersonalizzata(amicoselezionato, titoloLista));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            visualizzaListePersonalizzateAmiciFragment.getLvVisualizzaListePersonalizzateAmico().setEmptyView(visualizzaListePersonalizzateAmiciFragment.getTvEmptyListePersonalizzateAmico());
            visualizzaListePersonalizzateAmiciFragment.aggiornaLvVisualizzaListePersonalizzateAmico(adapterVisualizzaListePersonalizzateAmici);
            visualizzaListePersonalizzateAmiciFragment.togliProgressDialogCaricamento();
        }
    }

}

