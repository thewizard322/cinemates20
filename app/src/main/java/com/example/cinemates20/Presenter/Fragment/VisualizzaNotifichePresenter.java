package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Notifica;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;
import com.example.cinemates20.View.Fragment.VisualizzaListaPersonalizzataRaccomandataFragment;
import com.example.cinemates20.View.Fragment.VisualizzaNotificheFragment;
import com.example.cinemates20.View.Fragment.VisualizzaValutazioniProprieListePersonalizzateFragment;
import com.example.cinemates20.Widgets.AdapterVisualizzaNotifiche;

import java.util.ArrayList;
import java.util.Objects;

public class VisualizzaNotifichePresenter {
    private ArrayList<Notifica> notificheArrayList = new ArrayList<>();
    private AdapterVisualizzaNotifiche adapterVisualizzaNotifiche;
    private VisualizzaNotificheFragment visualizzaNotificheFragment;

    public VisualizzaNotifichePresenter(VisualizzaNotificheFragment visualizzaNotificheFragment) {
        this.visualizzaNotificheFragment = visualizzaNotificheFragment;
        initializeListener();
        initializeListView();
        prelevaNotifiche();
    }

    private void initializeListener(){
        Button bAggiornaNotifiche = visualizzaNotificheFragment.getbAggiornaNotifiche();
        bAggiornaNotifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prelevaNotifiche();
            }
        });
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
                    String titoloLista = notificaCliccata.getTitoloLista();
                    String usernameMittente = notificaCliccata.getUsernameMittente();
                    addVisualizzaListaPersonalizzateFragment(titoloLista, usernameMittente);
                }
                else if(notificaCliccata.getTipo().equals("VLP")) {
                    String titoloLista = notificaCliccata.getTitoloLista();
                    String usernameMittente = notificaCliccata.getUsernameMittente();
                    int likeOrDislike = notificaCliccata.getLikeOrDislike();
                    String commento = notificaCliccata.getCommento();
                    addVisualizzaValutazioniProprieListePersonalizzateFragment(titoloLista, usernameMittente, likeOrDislike, commento);
                }
            }
        });
    }

    private void addMostraFilmFragment(Film filmPreferitoRaccomandato){
        Bundle bundle = new Bundle();
        bundle.putSerializable("film", filmPreferitoRaccomandato);
        Fragment fg = new MostraFilmFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaNotificheFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void addVisualizzaListaPersonalizzateFragment(String titoloLista, String usernameMittente){
        Bundle bundle = new Bundle();
        bundle.putString("titoloLista", titoloLista);
        bundle.putString("usernameMittente", usernameMittente);
        Fragment fg = new VisualizzaListaPersonalizzataRaccomandataFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaNotificheFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void addVisualizzaValutazioniProprieListePersonalizzateFragment(String titoloLista, String usernameMittente, int likeOrDislike, String commento){
        Bundle bundle = new Bundle();
        bundle.putString("titoloLista", titoloLista);
        bundle.putString("usernameMittente", usernameMittente);
        bundle.putInt("likeOrDislike", likeOrDislike);
        bundle.putString("commento", commento);
        Fragment fg = new VisualizzaValutazioniProprieListePersonalizzateFragment();
        fg.setArguments(bundle);
        FragmentManager fm = visualizzaNotificheFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private void prelevaNotifiche(){
        VisualizzaNotifichePresenter.PrelievoNotificheTask prelievoNotificheTask = new VisualizzaNotifichePresenter.PrelievoNotificheTask();
        prelievoNotificheTask.execute();
    }

    public void accettaRichiestaAmicizia(String usernameMittente, String usernameDestinatario, String tipo){
        VisualizzaNotifichePresenter.AccettaRichiestaAmiciziaTask accettaRichiestaAmiciziaTask = new VisualizzaNotifichePresenter.AccettaRichiestaAmiciziaTask();
        accettaRichiestaAmiciziaTask.execute(usernameMittente, usernameDestinatario, tipo);
    }

    private class PrelievoNotificheTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            notificheArrayList.clear();
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

    private class AccettaRichiestaAmiciziaTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            visualizzaNotificheFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String usernameMittente = strings[0];
            String usernameDestinatario = strings[1];
            String tipo = strings[2];
            UtenteDAO utenteDAO = new UtenteDAO();
            boolean amiciAggiunti1 = utenteDAO.aggiungiCoppiaAmici(usernameMittente, usernameDestinatario);
            boolean amiciAggiunti2 = utenteDAO.aggiungiCoppiaAmici(usernameDestinatario, usernameMittente);

            NotificaDAO notificaDAO = new NotificaDAO();
            notificaDAO.rimuoviNotifica(usernameMittente, usernameDestinatario, tipo);
            notificaDAO.rimuoviNotifica(usernameDestinatario, usernameMittente, tipo);

            if(amiciAggiunti1 && amiciAggiunti2) {
                notificaDAO.inviaNotificaAmicizia(usernameDestinatario, usernameMittente, "RAA");
            }

            return (amiciAggiunti1 && amiciAggiunti2);
        }

        @Override
        protected void onPostExecute(Boolean amiciAggiunti) {
            visualizzaNotificheFragment.togliProgressDialogCaricamento();
            if(amiciAggiunti==false){
                visualizzaNotificheFragment.mostraAlertDialogOk("ERRORE","Non è possibile accettare la richiesta");
            }
            else {
                prelevaNotifiche();
                visualizzaNotificheFragment.mostraAlertDialogOk("AMICO AGGIUNTO", "Utente aggiunto agli amici");
            }
        }
    }

}
