package com.example.cinemates20.Presenter.Fragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.DAO.RecensioneDAO;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.View.Fragment.InserisciRecensioneFragment;

public class InserisciRecensionePresenter {

    Film filmSelezionato;
    InserisciRecensioneFragment inserisciRecensioneFragment;

    public InserisciRecensionePresenter(InserisciRecensioneFragment inserisciRecensioneFragment, Film filmSelezionato){
        this.inserisciRecensioneFragment = inserisciRecensioneFragment;
        this.filmSelezionato = filmSelezionato;
        initializeListener();
    }

    private void initializeListener(){
        Button bRececensisciInserisciRecensione = inserisciRecensioneFragment.getbRececensisciInserisciRecensione();
        bRececensisciInserisciRecensione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserisciRecensione();
            }
        });
    }

    private void inserisciRecensione(){
        String testoRecensione = inserisciRecensioneFragment.getTilTestoRecensioneInserisciRecensione()
                .getEditText().getText().toString();
        String votoRecensione = inserisciRecensioneFragment.getSpinnerVoto().getSelectedItem().toString();
        boolean campiNonVuoti = campiNonVuoti(testoRecensione,votoRecensione);
        if(campiNonVuoti == false){
            inserisciRecensioneFragment.mostraToast("Compilare tutti i campi richiesti");
        }
        else{
            InserisciRecensioneTask inserisciRecensioneTask = new InserisciRecensioneTask();
            inserisciRecensioneTask.execute(testoRecensione,votoRecensione);
        }
    }

    private boolean campiNonVuoti(String testo, String votoRecensione){
        if(testo.equals("") || votoRecensione.equals(""))
            return false;
        return true;
    }

    private void onReviewInsertSuccess(){
        inserisciRecensioneFragment.mostraAlertDialogOk("RECENSIONE INSERITA"
                ,"Recensione inserita con successo");
        backToMostraFilmFragment();
    }

    private void backToMostraFilmFragment(){
        FragmentManager fm = inserisciRecensioneFragment.getActivity().getSupportFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    private class InserisciRecensioneTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            RecensioneDAO recensioneDAO = new RecensioneDAO();
            int idFilm = filmSelezionato.getId();
            String username = Utente.getUtenteLoggato().getUsername();
            String testo = strings[0];
            String voto = strings[1];
            boolean recensioneInserita = recensioneDAO.inserisciRecensione(username,idFilm,testo,voto);
            return recensioneInserita;
        }

        @Override
        protected void onPostExecute(Boolean recensioneInserita) {
            if(recensioneInserita == false)
                inserisciRecensioneFragment.mostraToast("Hai già inserito una recensione per questo film");
            else
                onReviewInsertSuccess();
        }
    }

}
