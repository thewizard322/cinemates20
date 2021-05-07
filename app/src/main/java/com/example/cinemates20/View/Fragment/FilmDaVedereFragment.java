package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.FilmDaVederePresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterFilmDaVedere;
import com.example.cinemates20.Widgets.AdapterPreferiti;

public class FilmDaVedereFragment extends Fragment {

    private ListView lwFilmDaVedere;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyFilmDaVedere;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filmdavedere, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lwFilmDaVedere = view.findViewById(R.id.lwFilmDaVedere);
        tvEmptyFilmDaVedere=view.findViewById(R.id.tvEmptyFilmDaVedere);
        initializeProgressDialog();
        new FilmDaVederePresenter(this);
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
    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }


    public ListView getLwFilmDaVedere() {
        return lwFilmDaVedere;
    }

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgrssDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public TextView getTvEmptyFilmDaVedere(){
        return tvEmptyFilmDaVedere;
    }

    public void setAdapterLwDaVedere(AdapterFilmDaVedere adapterFilmDaVedere){
        lwFilmDaVedere.setAdapter(adapterFilmDaVedere);
    }

    public void aggiornaLwPreferiti(AdapterFilmDaVedere adapterFilmDaVedere){
        adapterFilmDaVedere.notifyDataSetChanged();
    }
}

