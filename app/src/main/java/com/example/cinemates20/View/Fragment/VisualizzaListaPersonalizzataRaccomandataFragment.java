package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.RaccomandaListaPersonalizzataPresenter;
import com.example.cinemates20.Presenter.Fragment.VisualizzaListaPersonalizzataRaccomandataPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.Widgets.AdapterVisualizzaListaPersonalizzataRaccomandata;

public class VisualizzaListaPersonalizzataRaccomandataFragment extends Fragment  {
    private ImageButton bAggiungiVisualizzaListePersonalizzate;
    private Button btRaccomandaListaPersonalizzata;
    private TextView tvTitoloLabelVisualizzaListaPers;
    private TextView tvTitoloVisualizzaListaPers;
    private TextView tvDescrizioneVisualizzaListaPers;
    private TextView tvDescrizioneVisualizzaListePersonalizzate;
    private ListView lvVisualizzaListePersonalizzate;
    private ProgressDialog progressDialogCaricamento;
    private TextView tvEmptyListePersonalizzate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizzalistapersonalizzataraccomandata, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bAggiungiVisualizzaListePersonalizzate = view.findViewById(R.id.bAggiungiVisualizzaListePersonalizzate);
        btRaccomandaListaPersonalizzata = view.findViewById(R.id.btRaccomandaListaPersonalizzata);
        tvTitoloLabelVisualizzaListaPers = view.findViewById(R.id.tvTitoloLabelVisualizzaListaPers);
        tvTitoloVisualizzaListaPers = view.findViewById(R.id.tvTitoloVisualizzaListaPers);
        tvDescrizioneVisualizzaListaPers = view.findViewById(R.id.tvDescrizioneVisualizzaListaPers);
        tvDescrizioneVisualizzaListePersonalizzate = view.findViewById(R.id.tvDescrizioneVisualizzaListePersonalizzate);
        lvVisualizzaListePersonalizzate = view.findViewById(R.id.lvVisualizzaListePersonalizzate);
        tvEmptyListePersonalizzate = view.findViewById(R.id.tvEmptyListePersonalizzate);
        initializeProgressDialog();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String titoloLista = bundle.getString("titoloLista");
            String usernameMittente = bundle.getString("usernameMittente");
            new VisualizzaListaPersonalizzataRaccomandataPresenter(this, titoloLista, usernameMittente);
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

    public ImageButton getbAggiungiVisualizzaListePersonalizzate() {
        return bAggiungiVisualizzaListePersonalizzate;
    }

    public Button getBtRaccomandaListaPersonalizzata(){
        return btRaccomandaListaPersonalizzata;
    }

    public String getTitoloLista(){
        return tvTitoloVisualizzaListaPers.getText().toString();
    }

    public ListView getLvVisualizzaListaPersonalizzataRaccomandata() {
        return lvVisualizzaListePersonalizzate;
    }

    public TextView getTvEmptyListePersonalizzate() {
        return tvEmptyListePersonalizzate;
    }

    public void setTextTitoloLista(String titoloLista) {
        tvTitoloVisualizzaListaPers.setText(titoloLista);
    }

    public void setTextDescrizione(String descrizione) {
        tvDescrizioneVisualizzaListePersonalizzate.setText(descrizione);
    }

    public void setAdapterLvAdapterVisualizzaListaPersonalizzataRaccomandata(AdapterVisualizzaListaPersonalizzataRaccomandata adapterVisualizzaListaPersonalizzataRaccomandata){
        lvVisualizzaListePersonalizzate.setAdapter(adapterVisualizzaListaPersonalizzataRaccomandata);
    }

    public void aggiornaLvVisualizzaListePersonalizzate(AdapterVisualizzaListaPersonalizzataRaccomandata adapterVisualizzaListaPersonalizzataRaccomandata){
        adapterVisualizzaListaPersonalizzataRaccomandata.notifyDataSetChanged();
    }


}
