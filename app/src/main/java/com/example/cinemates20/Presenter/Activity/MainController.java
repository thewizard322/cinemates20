package com.example.cinemates20.Presenter.Activity;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Activity.MainActivity;
import com.example.cinemates20.View.Fragment.HomeFragment;
import com.example.cinemates20.View.Fragment.LoginFragment;
import com.google.gson.Gson;

public class MainController {

    private MainActivity mainActivity;

    public MainController(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        checkUserLoggedIn();
    }

    public void checkUserLoggedIn(){
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("utenteLoggato", "");
        Utente utenteLoggato = gson.fromJson(json,Utente.class);
        if(utenteLoggato == null)
            addLoginFragment();
        else {
            Utente.setUtenteLoggato(utenteLoggato);
            addHomeFragment();
        }
    }

    public void addHomeFragment(){
        Fragment fg = new HomeFragment();
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).commit();
    }

    public void addLoginFragment() {
        Fragment fg = new LoginFragment();
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_main_activity, fg).commit();
    }

}
