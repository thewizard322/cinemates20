package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Notifica;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.View.Fragment.VisualizzaNotificheFragment;
import com.example.cinemates20.Widgets.AdapterVisualizzaNotifiche;

import java.util.ArrayList;
import java.util.Objects;

public class VisualizzaNotifichePresenter {
    private ArrayList<Notifica> notificheArrayList = new ArrayList<>();
    private AdapterVisualizzaNotifiche adapterVisualizzaNotifiche;
    private VisualizzaNotificheFragment visualizzaNotificheFragment;

    public VisualizzaNotifichePresenter(VisualizzaNotificheFragment visualizzaNotificheFragment) {
        this.visualizzaNotificheFragment = visualizzaNotificheFragment;
        initializeListView();
        prelevaNotifiche();
    }

    private void initializeListView() {
        ListView lvVisualizzaNotifiche = visualizzaNotificheFragment.getLvVisualizzaNotifiche();
        adapterVisualizzaNotifiche = new AdapterVisualizzaNotifiche(Objects.requireNonNull(visualizzaNotificheFragment.getContext()), visualizzaNotificheFragment, notificheArrayList, this);
        visualizzaNotificheFragment.setAdapterLvVisualizzaNotifiche(adapterVisualizzaNotifiche);
        lvVisualizzaNotifiche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notifica notificaCliccata = (Notifica)parent.getItemAtPosition(position);
                if(notificaCliccata.getTipo().equals("RFP")) {
                    int idF = notificaCliccata.getIdFilmPreferito();
                    String titoloFilmPref = notificaCliccata.getTitoloFilmPreferito();
                    String dataUscita = notificaCliccata.getDataUscitaPreferito();
                    String pathPoster = notificaCliccata.getPosterPathPreferito();

                    Film filmPreferitoRaccomandato = new Film(idF, titoloFilmPref, dataUscita, pathPoster);
                    addMostraFilmFragment(filmPreferitoRaccomandato);
                }
                else if(notificaCliccata.getTipo().equals("RLP")) {
                    //addVisualizzaListaPersonalizzateFragment(position);
                }
            }
        });
    }

    private void addMostraFilmFragment(Film filmPreferitoRaccomandato){
        System.out.println("---------------------------------------------P--------------------------------------");
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmPreferitoRaccomandato);
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaNotificheFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void prelevaNotifiche(){
        VisualizzaNotifichePresenter.PrelievoNotificheTask prelievoNotificheTask = new VisualizzaNotifichePresenter.PrelievoNotificheTask();
        prelievoNotificheTask.execute();
    }

    private class PrelievoNotificheTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            visualizzaNotificheFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            NotificaDAO notificaDAO = new NotificaDAO();
            notificheArrayList.addAll(notificaDAO.prelevaNotifiche(username));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            visualizzaNotificheFragment.getLvVisualizzaNotifiche().setEmptyView(visualizzaNotificheFragment.getTvEmptyVisualizzaNotifiche());
            visualizzaNotificheFragment.aggiornaLvVisualizzaNotifiche(adapterVisualizzaNotifiche);
            visualizzaNotificheFragment.togliProgressDialogCaricamento();
        }
    }

}
