package com.example.cinemates20.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cinemates20.Presenter.Activity.MainController;
import com.example.cinemates20.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainController(this);
    }

}