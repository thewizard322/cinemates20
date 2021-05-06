package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
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

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }


    public ListView getLwFilmDaVedere() {
        return lwFilmDaVedere;
    }
    public ProgressDialog getProgressDialogRicercaInCorso() {
        return progressDialogCaricamento;
    }
    public TextView getTvEmptyFilmDaVedere(){
        return tvEmptyFilmDaVedere;
    }
}

