package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.AmiciPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterAmici;

public class AmiciFragment extends Fragment {

    private ListView lwAmici;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyAmici;
    private Button btAggiungiAmico;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amici, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lwAmici = view.findViewById(R.id.lwAmici);
        tvEmptyAmici=view.findViewById(R.id.tvEmptyAmici);
        btAggiungiAmico=view.findViewById(R.id.btAggiungiAmico);
        initializeProgressDialog();
        new AmiciPresenter(this);
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


    public ListView getLwAmici() {
        return lwAmici;
    }

    public TextView getTvEmptyAmici(){
        return tvEmptyAmici;
    }

    public Button getBtAggiungiAmico(){return btAggiungiAmico;}

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgressDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public void setAdapterLwAmici(AdapterAmici adapterAmici){
        lwAmici.setAdapter(adapterAmici);
    }

    public void aggiornaLwAmici(AdapterAmici adapterAmici){
        adapterAmici.notifyDataSetChanged();
    }
}

