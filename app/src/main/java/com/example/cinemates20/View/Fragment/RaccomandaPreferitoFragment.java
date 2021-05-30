package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Presenter.Fragment.RaccomandaPreferitoPresenter;
import com.example.cinemates20.R;

public class RaccomandaPreferitoFragment extends Fragment {

    private TextView tvTitoloRaccomandaPreferito;
    private Spinner spinnerAmicoRaccomandaPreferito;
    private Button bInviaRaccomandaPreferito;
    private ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raccomandapreferito,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitoloRaccomandaPreferito = view.findViewById(R.id.tvTitoloRaccomandaPreferito);
        spinnerAmicoRaccomandaPreferito = view.findViewById(R.id.spinnerAmicoRaccomandaPreferito);
        bInviaRaccomandaPreferito = view.findViewById(R.id.bInviaRaccomandaPreferito);
        initializeProgressDialog();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Film film = (Film) bundle.getSerializable("film");
            new RaccomandaPreferitoPresenter(this,film);
        }
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public Button getbInviaRaccomandaPreferito(){
        return bInviaRaccomandaPreferito;
    }

    public String getUsernameAmico(){
        if(spinnerAmicoRaccomandaPreferito.getSelectedItem()!=null)
            return spinnerAmicoRaccomandaPreferito.getSelectedItem().toString();
        return "";
    }

    public Spinner getSpinnerAmicoRaccomandaPreferito(){
        return spinnerAmicoRaccomandaPreferito;
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public void mostraAlertDialogOk(String titolo, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(titolo)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void mostraProgressDialogCaricamento(){
        progressDialogCaricamento.show();
    }

    public void togliProgrssDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public void setAdapterSpinnerTitoloLista(ArrayAdapter<String> arrayAdapter){
        spinnerAmicoRaccomandaPreferito.setAdapter(arrayAdapter);
    }

    public void setTvTitoloRaccomandaPreferito(String titoloFilm){
        tvTitoloRaccomandaPreferito.setText("Condividi '"+titoloFilm+"' ai tuoi amici");
    }

}
