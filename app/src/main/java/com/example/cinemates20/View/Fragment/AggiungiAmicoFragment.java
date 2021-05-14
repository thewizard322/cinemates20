package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.AggiungiAmicoPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterAggiungiAmici;

public class AggiungiAmicoFragment extends Fragment {

    private EditText etUsername;
    private Button btRicercaUtente;
    private ListView lwUtenti;
    private ProgressDialog progressDialogRicercaInCorso;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyRicercaUtente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungiamico,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etUsername = view.findViewById(R.id.etUsername);
        btRicercaUtente = view.findViewById(R.id.bRicercaUtente);
        lwUtenti = view.findViewById(R.id.lwUtenti);
        tvEmptyRicercaUtente = view.findViewById(R.id.tvEmptyRicercaUtente);
        initializeProgressDialog();
        new AggiungiAmicoPresenter(this);
    }

    private void initializeProgressDialog(){
        progressDialogRicercaInCorso = new ProgressDialog(getActivity());
        progressDialogRicercaInCorso.setTitle("");
        progressDialogRicercaInCorso.setMessage("Ricerca in corso");
        progressDialogRicercaInCorso.setCancelable(false);

        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public void mostraAlertDialogOk(String titolo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(titolo)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public EditText getEtUsername() { return etUsername; }

    public String getUsername(){ return etUsername.getText().toString(); }

    public Button getbRicercaUtenti() {
        return btRicercaUtente;
    }

    public ListView getLwUtenti() {
        return lwUtenti;
    }

    public void mostraProgressDialogRicercaInCorso(){
        progressDialogRicercaInCorso.show();
    }

    public void togliProgressDialogRicercaInCorso(){
        progressDialogRicercaInCorso.dismiss();
    }

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgressDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public TextView getTvEmptyRicercaUtente(){
        return tvEmptyRicercaUtente;
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getApplicationWindowToken(),0);
    }

    public void setAdapterLwRicercaUtente(AdapterAggiungiAmici adapterAggiungiAmici){
        lwUtenti.setAdapter(adapterAggiungiAmici);
    }

    public void aggiornaLwUtenti(AdapterAggiungiAmici adapterAggiungiAmici){
        adapterAggiungiAmici.notifyDataSetChanged();
    }
}
