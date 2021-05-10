package com.example.cinemates20.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Presenter.Fragment.InserisciRecensionePresenter;
import com.example.cinemates20.Presenter.Fragment.RaccomandaPreferitoPresenter;
import com.example.cinemates20.R;

public class RaccomandaPreferitoFragment extends Fragment {

    private TextView tvTitoloRaccomandaPreferito;
    private Spinner spinnerAmicoRaccomandaPreferito;
    private Button bInviaRaccomandaPreferito;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitoloRaccomandaPreferito = view.findViewById(R.id.tvTitoloRaccomandaPreferito);
        spinnerAmicoRaccomandaPreferito = view.findViewById(R.id.spinnerAmicoRaccomandaPreferito);
        bInviaRaccomandaPreferito = view.findViewById(R.id.bInviaRaccomandaPreferito);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new RaccomandaPreferitoPresenter(this,film);
        }
    }

    public Button getbInviaRaccomandaPreferito(){
        return bInviaRaccomandaPreferito;
    }

}
