package com.example.cinemates20.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Presenter.Fragment.MostraFilmPresenter;
import com.example.cinemates20.R;

public class MostraFilmFragment extends Fragment {

    ListView lwMostraFilm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mostrafilm,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lwMostraFilm = view.findViewById(R.id.lwMostraFilm);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new MostraFilmPresenter(this, film);
        }
    }

    public ListView getLwMostraFilm(){
        return lwMostraFilm;
    }
}
