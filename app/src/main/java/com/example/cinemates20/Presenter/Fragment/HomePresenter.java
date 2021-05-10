package com.example.cinemates20.Presenter.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AmiciFragment;
import com.example.cinemates20.View.Fragment.FilmDaVedereFragment;
import com.example.cinemates20.View.Fragment.HomeFragment;
import com.example.cinemates20.View.Fragment.LoginFragment;
import com.example.cinemates20.View.Fragment.PreferitiFragment;
import com.example.cinemates20.View.Fragment.RicercaFilmFragment;
import com.example.cinemates20.View.Fragment.VisualizzaListePersonalizzateFragment;

public class HomePresenter {

    private HomeFragment homeFragment;

    public HomePresenter(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
        initializeListener();
    }

    public void initializeListener(){
        Button btRicercaFilmHome = homeFragment.getBtRicercaFilmHome();
        Button btLogoutHome = homeFragment.getBtLogoutHome();
        Button btPreferiti = homeFragment.getBtPreferitiHome();
        Button btFilmDaVedere = homeFragment.getBtFilmDaVedereHome();
        Button btListePersonalizzateHome = homeFragment.getBtListePersonalizzateHome();
        Button btAmici= homeFragment.getBtAmiciHome();

        btRicercaFilmHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceRicercaFilmFragment();
            }
        });
        btLogoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUserLoggedIn();
                replaceLoginFragment();
                homeFragment.mostraToast("Logout eseguito");
            }
        });
        btPreferiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { replacePreferitiFragment();}
        });
        btFilmDaVedere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { replaceFilmDaVedereFragment();}
        });
        btListePersonalizzateHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { replaceVisualizzaListePersonalizzateFragment();}
        });
        btAmici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { replaceAmiciFragment(); }
        });
    }

    public void removeUserLoggedIn(){
        SharedPreferences sharedPreferences = homeFragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("utenteLoggato").commit();
        Utente.finalizeUtenteLoggato();
    }

    private void replaceRicercaFilmFragment(){
        Fragment fg = new RicercaFilmFragment();
        FragmentManager fm = homeFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }

    public void replaceLoginFragment(){
        Fragment fg = new LoginFragment();
        FragmentManager fm = homeFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).commit();
    }

    public void replacePreferitiFragment(){
        Fragment fg= new PreferitiFragment();
        FragmentManager fm= homeFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();

    }

    public void replaceFilmDaVedereFragment(){
        Fragment fg= new FilmDaVedereFragment();
        FragmentManager fm= homeFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();

    }

    public void replaceVisualizzaListePersonalizzateFragment(){
        Fragment fg= new VisualizzaListePersonalizzateFragment();
        FragmentManager fm= homeFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }
    public void replaceAmiciFragment(){
        Fragment fg= new AmiciFragment();
        FragmentManager fm= homeFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_main_activity, fg).addToBackStack(null).commit();
    }
}
