package com.example.cinemates20.View.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.RicercaFilmPresenter;
import com.example.cinemates20.R;

public class RicercaFilmFragment extends Fragment {

    private EditText etTitoloRicercaFilm;
    private Button bRicercaFilm;
    private ListView lwRicercaFilm;
    private ProgressDialog progressDialogRicercaInCorso;
    private TextView tvEmptyRicercaFilm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ricercafilm,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTitoloRicercaFilm = view.findViewById(R.id.etTitoloRicercaFilm);
        bRicercaFilm = view.findViewById(R.id.bRicercaFilm);
        lwRicercaFilm = view.findViewById(R.id.lwRicercaFilm);
        tvEmptyRicercaFilm = view.findViewById(R.id.tvEmptyRicercaFilm);
        initializeProgressDialog();
        new RicercaFilmPresenter(this);
    }

    private void initializeProgressDialog(){
        progressDialogRicercaInCorso = new ProgressDialog(getActivity());
        progressDialogRicercaInCorso.setTitle("");
        progressDialogRicercaInCorso.setMessage("Ricerca in corso");
        progressDialogRicercaInCorso.setCancelable(false);
    }

    public String getTitolo(){
        return etTitoloRicercaFilm.getText().toString();
    }

    public EditText getEtTitoloRicercaFilm(){
        return etTitoloRicercaFilm;
    }

    public Button getbRicercaFilm() {
        return bRicercaFilm;
    }

    public ListView getLwRicercaFilm() {
        return lwRicercaFilm;
    }

    public ProgressDialog getProgressDialogRicercaInCorso(){
        return progressDialogRicercaInCorso;
    }

    public TextView getTvEmptyRicercaFilm(){
        return tvEmptyRicercaFilm;
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getApplicationWindowToken(),0);
    }
}
