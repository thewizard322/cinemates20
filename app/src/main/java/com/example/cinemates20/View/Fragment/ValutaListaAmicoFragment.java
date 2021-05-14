package com.example.cinemates20.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.ValutaListaAmicoPresenter;
import com.example.cinemates20.Presenter.Fragment.VisualizzaListePersonalizzateAmiciPresenter;
import com.example.cinemates20.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValutaListaAmicoFragment extends Fragment {

    TextView tvTitoloListaValuta;
    TextInputLayout tilCommentoValutaListaAmico;
    Button bValutaListaAmico;
    RadioGroup rgValutazioneRapida;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_valutalistaamico, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitoloListaValuta = view.findViewById(R.id.tvTitoloListaValuta);
        tilCommentoValutaListaAmico = view.findViewById(R.id.tilCommentoValutaListaAmico);
        bValutaListaAmico = view.findViewById(R.id.bValutaListaAmico);
        rgValutazioneRapida = view.findViewById(R.id.rgValutazioneRapida);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String titoloLista = bundle.getString("titoloLista");
            new ValutaListaAmicoPresenter(this,titoloLista);
        }
    }

    public TextView getTvTitoloListaValuta() {
        return tvTitoloListaValuta;
    }

    public TextInputLayout getTilCommentoValutaListaAmico() {
        return tilCommentoValutaListaAmico;
    }

    public Button getbValutaListaAmico() {
        return bValutaListaAmico;
    }

    public RadioGroup getRgValutazioneRapida() {
        return rgValutazioneRapida;
    }



}
