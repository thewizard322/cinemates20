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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cinemates20.Presenter.Fragment.ValutaListaAmicoPresenter;
import com.example.cinemates20.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValutaListaAmicoFragment extends Fragment {

    TextView tvTitoloListaValuta;
    TextInputLayout tilCommentoValutaListaAmico;
    Button bValutaListaAmico;
    RadioGroup rgValutazioneRapida;
    RadioButton rbLike;
    RadioButton rbDislike;
    ProgressDialog progressDialogCaricamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_valutalistaamico, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitoloListaValuta = view.findViewById(R.id.tvTitoloListaValuta);
        tilCommentoValutaListaAmico = view.findViewById(R.id.tilCommentoValutaListaAmico);
        bValutaListaAmico = view.findViewById(R.id.bValutaListaAmico);
        rgValutazioneRapida = view.findViewById(R.id.rgValutazioneRapida);
        rbLike = view.findViewById(R.id.rbLike);
        rbDislike = view.findViewById(R.id.rbDislike);
        tilCommentoValutaListaAmico.getEditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
        tilCommentoValutaListaAmico.getEditText().setRawInputType(InputType.TYPE_CLASS_TEXT);

        initializeProgressDialog();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String titoloLista = bundle.getString("titoloLista");
            String utenteLista = bundle.getString("utenteLista");
            new ValutaListaAmicoPresenter(this,titoloLista,utenteLista);
        }
    }

    private void initializeProgressDialog(){
        progressDialogCaricamento = new ProgressDialog(getActivity());
        progressDialogCaricamento.setTitle("");
        progressDialogCaricamento.setMessage("Caricamento");
        progressDialogCaricamento.setCancelable(false);
    }

    public Button getbValutaListaAmico() {
        return bValutaListaAmico;
    }


    public boolean rbLikeIsChecked(){
        return rbLike.isChecked();
    }

    public boolean rbDislikeIsChecked(){
        return rbDislike.isChecked();
    }

    public void mostraToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public String getCommentoValutazone(){
        return tilCommentoValutaListaAmico.getEditText().getText().toString();
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

    public void togliProgressDialogCaricamento(){
        progressDialogCaricamento.dismiss();
    }

    public void setTextTvTitoloListaValuta(String titoloLista){
        tvTitoloListaValuta.setText("Stai valutando la lista '"+titoloLista+"'");
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                    .getApplicationWindowToken(),0);
    }

}
