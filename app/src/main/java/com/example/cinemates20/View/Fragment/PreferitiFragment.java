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

import com.example.cinemates20.Presenter.Fragment.PreferitiPresenter;
import com.example.cinemates20.R;

public class PreferitiFragment extends Fragment {
    private ListView lwPreferiti;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyPreferiti;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preferiti, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lwPreferiti = view.findViewById(R.id.lwPreferiti);
        tvEmptyPreferiti=view.findViewById(R.id.tvEmptyPreferiti);
        initializeProgressDialog();
        new PreferitiPresenter(this);
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }


    public ListView getLwPreferiti() {
        return lwPreferiti;
    }
    public ProgressDialog getProgressDialogRicercaInCorso() {
        return progressDialogCaricamento;
    }
    public TextView getTvEmptyPreferiti(){
        return tvEmptyPreferiti;
    }
}

