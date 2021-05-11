package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;

import com.example.cinemates20.DAO.NotificaDAO;
import com.example.cinemates20.Model.Notifica;
import com.example.cinemates20.Model.Utente;
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
        adapterVisualizzaNotifiche = new AdapterVisualizzaNotifiche(Objects.requireNonNull(visualizzaNotificheFragment.getContext()), visualizzaNotificheFragment, notificheArrayList, this);
        visualizzaNotificheFragment.setAdapterLvVisualizzaNotifiche(adapterVisualizzaNotifiche);
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
