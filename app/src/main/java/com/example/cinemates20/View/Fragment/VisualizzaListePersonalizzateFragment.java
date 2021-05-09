package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.VisualizzaListePersonalizzatePresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterVisualizzaListePersonalizzate;

public class VisualizzaListePersonalizzateFragment extends Fragment {

    private ImageButton bAggiungiVisualizzaListePersonalizzate;
    private TextView tvTitoloVisualizzaListaPers;
    private Spinner spinnerTitoloVisualizzaListePersonalizzate;
    private TextView tvDescrizioneVisualizzaListaPers;
    private TextView tvDescrizioneVisualizzaListePersonalizzate;
    private ListView lvVisualizzaListePersonalizzate;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyListePersonalizzate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizzalistepersonalizzate, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bAggiungiVisualizzaListePersonalizzate = view.findViewById(R.id.bAggiungiVisualizzaListePersonalizzate);
        tvTitoloVisualizzaListaPers = view.findViewById(R.id.tvTitoloVisualizzaListaPers);
        spinnerTitoloVisualizzaListePersonalizzate = view.findViewById(R.id.spinnerTitoloVisualizzaListePersonalizzate);
        tvDescrizioneVisualizzaListaPers = view.findViewById(R.id.tvDescrizioneVisualizzaListaPers);
        tvDescrizioneVisualizzaListePersonalizzate = view.findViewById(R.id.tvDescrizioneVisualizzaListePersonalizzate);
        lvVisualizzaListePersonalizzate = view.findViewById(R.id.lvVisualizzaListePersonalizzate);
        tvEmptyListePersonalizzate = view.findViewById(R.id.tvEmptyListePersonalizzate);
        initializeProgressDialog();
        new VisualizzaListePersonalizzatePresenter(this);
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

    public ImageButton getbAggiungiVisualizzaListePersonalizzate() {
        return bAggiungiVisualizzaListePersonalizzate;
    }

    public Spinner getSpinnerTitoloVisualizzaListePersonalizzate() {
        return spinnerTitoloVisualizzaListePersonalizzate;
    }

    public String getTitoloLista(){
        return spinnerTitoloVisualizzaListePersonalizzate.getSelectedItem().toString();
    }

    public TextView getTvDescrizioneVisualizzaListePersonalizzate() {
        return tvDescrizioneVisualizzaListePersonalizzate;
    }

    public ListView getLvVisualizzaListePersonalizzate() {
        return lvVisualizzaListePersonalizzate;
    }

    public TextView getTvEmptyListePersonalizzate() {
        return tvEmptyListePersonalizzate;
    }

    public TextView getTvTitoloVisualizzaListaPers() {
        return tvTitoloVisualizzaListaPers;
    }

    public TextView getTvDescrizioneVisualizzaListaPers() {
        return tvDescrizioneVisualizzaListaPers;
    }

    public void setTextDescrizione(String descrizione) {
        tvDescrizioneVisualizzaListePersonalizzate.setText(descrizione);
    }

    public void setAdapterSpinnerTitoloLista(ArrayAdapter<String> arrayAdapter){
        spinnerTitoloVisualizzaListePersonalizzate.setAdapter(arrayAdapter);
    }

    public void setAdapterLvVisualizzaListePersonalizzate(AdapterVisualizzaListePersonalizzate adapterLvVisualizzaListePersonalizzate){
        lvVisualizzaListePersonalizzate.setAdapter(adapterLvVisualizzaListePersonalizzate);
    }

    public void aggiornaLvVisualizzaListePersonalizzate(AdapterVisualizzaListePersonalizzate adapterLvVisualizzaListePersonalizzate){
        adapterLvVisualizzaListePersonalizzate.notifyDataSetChanged();
    }

}
