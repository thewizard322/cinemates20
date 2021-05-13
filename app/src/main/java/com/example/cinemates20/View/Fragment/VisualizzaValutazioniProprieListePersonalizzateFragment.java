package com.example.cinemates20.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.VisualizzaValutazioniProprieListePersonalizzatePresenter;
import com.example.cinemates20.R;

public class VisualizzaValutazioniProprieListePersonalizzateFragment extends Fragment {
    private TextView tvIntestazioneVisualizzaValutazioniProprieListePersonalizzate;
    private TextView tvLabelValutazioneVisualizzaValutazioniProprieListePersonalizzate;
    private TextView tvTestoValutazioneVisualizzaValutazioniProprieListePersonalizzate;
    private TextView tvLabelRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate;
    private TextView tvRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visualizzavalutazioniproprielistepersonalizzate, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvIntestazioneVisualizzaValutazioniProprieListePersonalizzate = view.findViewById(R.id.tvIntestazioneVisualizzaValutazioniProprieListePersonalizzate);
        tvLabelValutazioneVisualizzaValutazioniProprieListePersonalizzate = view.findViewById(R.id.tvLabelValutazioneVisualizzaValutazioniProprieListePersonalizzate);
        tvTestoValutazioneVisualizzaValutazioniProprieListePersonalizzate = view.findViewById(R.id.tvTestoValutazioneVisualizzaValutazioniProprieListePersonalizzate);
        tvLabelRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate = view.findViewById(R.id.tvLabelRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate);
        tvRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate = view.findViewById(R.id.tvRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String titoloLista = bundle.getString("titoloLista");
            String usernameMittente = bundle.getString("usernameMittente");
            int likeOrDislike = bundle.getInt("likeOrDislike");
            String commento = bundle.getString("commento");
            new VisualizzaValutazioniProprieListePersonalizzatePresenter(this, titoloLista, usernameMittente, likeOrDislike, commento);
        }
    }

    public void setTextTestoValutazione(String testoValutazione) {
        tvTestoValutazioneVisualizzaValutazioniProprieListePersonalizzate.setText(testoValutazione);
    }

    public void setTextRapida(String rapida) {
        tvRapidaValutazioneVisualizzaValutazioniProprieListePersonalizzate.setText(rapida);
    }
}
