package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.UtenteDAO;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AggiungiAmicoFragment;
import com.example.cinemates20.View.Fragment.AmiciFragment;
import com.example.cinemates20.View.Fragment.RicercaFilmFragment;
import com.example.cinemates20.Widgets.AdapterAmici;

import java.util.ArrayList;
import java.util.Objects;

public class AmiciPresenter {
    private ArrayList<String> amici=new ArrayList<String>();
    private AdapterAmici adapterAmici;
    private AmiciFragment amiciFragment;

    public AmiciPresenter(AmiciFragment amiciFragment) {
        this.amiciFragment= amiciFragment;
        initializeListView();
        initializeListener();
        prelevaAmici();
    }

    private void initializeListView() {
        adapterAmici = new AdapterAmici(Objects.requireNonNull(amiciFragment.getContext()), amiciFragment, amici,this);
        amiciFragment.setAdapterLwAmici(adapterAmici);
    }
  private void initializeListener(){
      Button btAggiungiAmico=amiciFragment.getBtAggiungiAmico();
      btAggiungiAmico.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              replaceAggiungiAmicoFragment();
          }
      });
  }
    private void prelevaAmici(){
        PrelievoAmiciTask prelievoAmiciTask=new PrelievoAmiciTask();
        prelievoAmiciTask.execute();
    }
    private void replaceAggiungiAmicoFragment(){
        Fragment fg = new AggiungiAmicoFragment();
        FragmentManager fm = amiciFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    private class PrelievoAmiciTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            amiciFragment.mostraProgressDialogCaricamento();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String username = Utente.getUtenteLoggato().getUsername();
            UtenteDAO utenteDAO=new UtenteDAO();
            amici.addAll(utenteDAO.prelevaUsernameAmici(Utente.getUtenteLoggato().getUsername()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            amiciFragment.getLwAmici().setEmptyView(amiciFragment.getTvEmptyAmici());
            amiciFragment.aggiornaLwAmici(adapterAmici);
            amiciFragment.togliProgressDialogCaricamento();
        }
    }
}