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

import com.example.cinemates20.Presenter.Fragment.PreferitiPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterPreferiti;

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


    public ListView getLwPreferiti() {
        return lwPreferiti;
    }

    public TextView getTvEmptyPreferiti(){
        return tvEmptyPreferiti;
    }

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgrssDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public void setAdapterLwPreferiti(AdapterPreferiti adapterPreferiti){
        lwPreferiti.setAdapter(adapterPreferiti);
    }

    public void aggiornaLwPreferiti(AdapterPreferiti adapterPreferiti){
        adapterPreferiti.notifyDataSetChanged();
    }
}

