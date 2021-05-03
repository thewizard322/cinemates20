package com.example.cinemates20.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Presenter.Fragment.InserisciRecensionePresenter;
import com.example.cinemates20.R;
import com.google.android.material.textfield.TextInputLayout;

public class InserisciRecensioneFragment extends Fragment {

    Spinner spinnerVoto;
    TextInputLayout tilTestoRecensioneInserisciRecensione;
    Button bRececensisciInserisciRecensione;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inseriscirecensione,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerVoto = view.findViewById(R.id.spinnerValutazioneRecensione);
        tilTestoRecensioneInserisciRecensione = view.findViewById(R.id.tilTestoRecensioneInserisciRecensione);
        bRececensisciInserisciRecensione = view.findViewById(R.id.bRececensisciInserisciRecensione);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new InserisciRecensionePresenter(this,film);
        }
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public Spinner getSpinnerVoto() {
        return spinnerVoto;
    }

    public TextInputLayout getTilTestoRecensioneInserisciRecensione() {
        return tilTestoRecensioneInserisciRecensione;
    }

    public Button getbRececensisciInserisciRecensione() {
        return bRececensisciInserisciRecensione;
    }
}
