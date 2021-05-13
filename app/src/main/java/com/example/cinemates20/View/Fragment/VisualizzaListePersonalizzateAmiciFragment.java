package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.RaccomandaListaPersonalizzataPresenter;
import com.example.cinemates20.Presenter.Fragment.VisualizzaListePersonalizzateAmiciPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterVisualizzaListePersonalizzateAmici;

public class VisualizzaListePersonalizzateAmiciFragment extends Fragment {

    private Button btValutaListaPersonalizzataAmico;
    private TextView tvTitoloVisualizzaListaPersAmici;
    private Spinner spinnerTitoloVisualizzaListePersonalizzateAmici;
    private TextView tvDescrizione;
    private TextView tvDescrizioneVisualizzaListePersonalizzateAmici;
    private ListView lvVisualizzaListePersonalizzateAmico;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyListePersonalizzateAmico;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizzalistepersonalizzateamici, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btValutaListaPersonalizzataAmico = view.findViewById(R.id.btValutaListaPersonalizzataAmico);
        tvTitoloVisualizzaListaPersAmici = view.findViewById(R.id.tvTitoloRowVisualizzaListePersonalizzateAmici);
        spinnerTitoloVisualizzaListePersonalizzateAmici = view.findViewById(R.id.spinnerTitoloVisualizzaListePersonalizzateAmico);
        tvDescrizione = view.findViewById(R.id.tvDescrizione);
        tvDescrizioneVisualizzaListePersonalizzateAmici = view.findViewById(R.id.tvDescrizioneListaPersAmico);
        lvVisualizzaListePersonalizzateAmico = view.findViewById(R.id.lvVisualizzaListePersonalizzateAmico);
        tvEmptyListePersonalizzateAmico = view.findViewById(R.id.tvEmptyListePersonalizzateAmico);
        initializeProgressDialog();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String amico = bundle.getString("amico");
            new VisualizzaListePersonalizzateAmiciPresenter(this,amico);
        }
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


    public Button getBtValutaListaPersonalizzataAmicoa(){
        return btValutaListaPersonalizzataAmico;
    }

    public Spinner getSpinnerTitoloVisualizzaListePersonalizzateAmici() {
        return spinnerTitoloVisualizzaListePersonalizzateAmici;
    }

    public String getTitoloLista(){
        if(spinnerTitoloVisualizzaListePersonalizzateAmici.getSelectedItem()!=null)
            return spinnerTitoloVisualizzaListePersonalizzateAmici.getSelectedItem().toString();
        return "";
    }

    public TextView getTvDescrizioneVisualizzaListePersonalizzateAmici() {
        return tvDescrizioneVisualizzaListePersonalizzateAmici;
    }

    public ListView getLvVisualizzaListePersonalizzateAmico() {
        return lvVisualizzaListePersonalizzateAmico;
    }

    public TextView getTvEmptyListePersonalizzateAmico() {
        return tvEmptyListePersonalizzateAmico;
    }

    public TextView getTvTitoloVisualizzaListaPersAmici() {
        return tvTitoloVisualizzaListaPersAmici;
    }

    public TextView getTvDescrizioneVisualizzaListaPersAmici() {
        return tvDescrizioneVisualizzaListePersonalizzateAmici;
    }

    public void setTextDescrizione(String descrizione) {
        tvDescrizione.setText(descrizione);
    }

    public void setAdapterSpinnerTitoloLista(ArrayAdapter<String> arrayAdapter){
        spinnerTitoloVisualizzaListePersonalizzateAmici.setAdapter(arrayAdapter);
    }

    public void setAdapterVisualizzaListePersonalizzateAmico(AdapterVisualizzaListePersonalizzateAmici adapterVisualizzaListePersonalizzateAmici) {
        lvVisualizzaListePersonalizzateAmico.setAdapter(adapterVisualizzaListePersonalizzateAmici);
    }

    public void aggiornaLvVisualizzaListePersonalizzateAmico(AdapterVisualizzaListePersonalizzateAmici adapterVisualizzaListePersonalizzateAmici){
        adapterVisualizzaListePersonalizzateAmici.notifyDataSetChanged();
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

}
