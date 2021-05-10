package com.example.cinemates20.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.HomePresenter;
import com.example.cinemates20.R;

public class HomeFragment extends Fragment {

    private TextView txtBenvenuto;
    private Button btRicercaFilmHome;
    private Button btAmiciHome;
    private Button btPreferitiHome;
    private Button btListePersonalizzateHome;
    private Button btFilmDaVedereHome;
    private Button btNotificheHome;
    private Button btCondividiFilmPreferitoHome;
    private Button btCondividiListaPersonalizzataHome;
    private Button btLogoutHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btRicercaFilmHome = view.findViewById(R.id.btRicercaFilmHome);
        btAmiciHome = view.findViewById(R.id.btAmiciHome);
        btPreferitiHome = view.findViewById(R.id.btPreferitiHome);
        btListePersonalizzateHome = view.findViewById(R.id.btListePersonalizzateHome);
        btFilmDaVedereHome = view.findViewById(R.id.btFilmDaVedereHome);
        btNotificheHome = view.findViewById(R.id.btNotificheHome);
        btCondividiFilmPreferitoHome = view.findViewById(R.id.btCondividiFilmPreferitoHome);
        btCondividiListaPersonalizzataHome = view.findViewById(R.id.btCondividiListaPersonalizzataHome);
        btLogoutHome = view.findViewById(R.id.btLogoutHome);

        new HomePresenter(this);
    }

    public Button getBtRicercaFilmHome() {
        return btRicercaFilmHome;
    }

    public Button getBtLogoutHome(){
        return btLogoutHome;
    }

    public Button getBtPreferitiHome() { return btPreferitiHome; }

    public Button getBtFilmDaVedereHome() { return btFilmDaVedereHome; }

    public Button getBtListePersonalizzateHome() { return btListePersonalizzateHome; }

    public Button getBtNotificheHome() {
        return btNotificheHome;
    }

    public Button getBtAmiciHome(){return btAmiciHome;}

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }


}
