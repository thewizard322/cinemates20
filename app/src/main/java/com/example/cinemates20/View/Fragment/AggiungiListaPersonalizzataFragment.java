package com.example.cinemates20.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.AggiungiListaPersonalizzataPresenter;
import com.example.cinemates20.R;
import com.google.android.material.textfield.TextInputLayout;

public class AggiungiListaPersonalizzataFragment extends Fragment {
    TextInputLayout tilTitoloListaAggiungiListaPersonalizzata;
    TextInputLayout tilTestoDescrizioneAggiungiListaPersonalizzata;
    Button bCreaListaAggiungiListaPersonalizzata;
    private ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aggiungilistapersonalizzata, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilTitoloListaAggiungiListaPersonalizzata = view.findViewById(R.id.tilTitoloListaAggiungiListaPersonalizzata);
        tilTestoDescrizioneAggiungiListaPersonalizzata = view.findViewById(R.id.tilTestoDescrizioneAggiungiListaPersonalizzata);
        bCreaListaAggiungiListaPersonalizzata = view.findViewById(R.id.bCreaListaAggiungiListaPersonalizzata);
        tilTestoDescrizioneAggiungiListaPersonalizzata.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
        tilTestoDescrizioneAggiungiListaPersonalizzata.getEditText().setRawInputType(InputType.TYPE_CLASS_TEXT);
        initializeProgressDialog();

        new AggiungiListaPersonalizzataPresenter(this);
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Creazione lista personalizzata");
        progressDialogCaricamento.setCancelable(false);
    }

    public void mostraProgressDialog(){
        progressDialogCaricamento.show();
    }

    public void togliProgressDialog(){
        progressDialogCaricamento.dismiss();
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

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getApplicationWindowToken(),0);
    }

    public TextInputLayout getTilTitoloListaAggiungiListaPersonalizzata() {
        return tilTitoloListaAggiungiListaPersonalizzata;
    }

    public TextInputLayout getTilTestoDescrizioneAggiungiListaPersonalizzata() {
        return tilTestoDescrizioneAggiungiListaPersonalizzata;
    }

    public String getTitoloLista(){
        return tilTitoloListaAggiungiListaPersonalizzata.getEditText().getText().toString();
    }

    public String getDescrizioneLista(){
        return tilTestoDescrizioneAggiungiListaPersonalizzata.getEditText().getText().toString();
    }

    public Button getbCreaListaAggiungiListaPersonalizzata() {
        return bCreaListaAggiungiListaPersonalizzata;
    }

}