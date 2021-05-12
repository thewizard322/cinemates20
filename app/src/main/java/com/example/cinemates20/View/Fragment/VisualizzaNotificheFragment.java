package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.VisualizzaNotifichePresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterVisualizzaNotifiche;

public class VisualizzaNotificheFragment extends Fragment {

    private TextView tvNotifiche;
    private ListView lvVisualizzaNotifiche;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyVisualizzaNotifiche;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizzanotifiche, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvNotifiche = view.findViewById(R.id.tvNotifiche);
        lvVisualizzaNotifiche = view.findViewById(R.id.lvVisualizzaNotifiche);
        tvEmptyVisualizzaNotifiche = view.findViewById(R.id.tvEmptyVisualizzaNotifiche);
        initializeProgressDialog();
        new VisualizzaNotifichePresenter(this);
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

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgressDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public void setAdapterLvVisualizzaNotifiche(AdapterVisualizzaNotifiche adapterVisualizzaNotifiche){
        lvVisualizzaNotifiche.setAdapter(adapterVisualizzaNotifiche);
    }

    public void aggiornaLvVisualizzaNotifiche(AdapterVisualizzaNotifiche adapterVisualizzaNotifiche){
        adapterVisualizzaNotifiche.notifyDataSetChanged();
    }

    public ListView getLvVisualizzaNotifiche() {
        return lvVisualizzaNotifiche;
    }

    public TextView getTvEmptyVisualizzaNotifiche() {
        return tvEmptyVisualizzaNotifiche;
    }
}
