package com.example.cinemates20.Widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.RequestOptions;
import com.example.cinemates20.Model.Notifica;
import com.example.cinemates20.Presenter.Fragment.VisualizzaNotifichePresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.VisualizzaNotificheFragment;

import java.util.ArrayList;

public class AdapterVisualizzaNotifiche extends ArrayAdapter<Notifica>  {
    private Context context;
    private ArrayList<Notifica> notificheArrayList;
    private VisualizzaNotificheFragment visualizzaNotificheFragment;
    private VisualizzaNotifichePresenter visualizzaNotifichePresenter;

    public AdapterVisualizzaNotifiche(@NonNull Context context, VisualizzaNotificheFragment visualizzaNotificheFragment, ArrayList<Notifica> notificheArrayList, VisualizzaNotifichePresenter visualizzaNotifichePresenter) {
        super(context, R.layout.row_visualizzanotifiche, notificheArrayList);
        this.context = context;
        this.notificheArrayList = notificheArrayList;
        this.visualizzaNotificheFragment = visualizzaNotificheFragment;
        this.visualizzaNotifichePresenter = visualizzaNotifichePresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterVisualizzaNotifiche.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = visualizzaNotificheFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_visualizzanotifiche,null,true);
            holder = new AdapterVisualizzaNotifiche.ViewHolder();
            holder.tvTestoNotifica = rootView.findViewById(R.id.tvTestoNotifica);
            holder.bAggiungiAmicoNotifica = rootView.findViewById(R.id.bAggiungiAmicoNotifica);
            rootView.setTag(holder);
        }
        else{
            holder = (AdapterVisualizzaNotifiche.ViewHolder) rootView.getTag();
        }

        Notifica notifica = notificheArrayList.get(position);
        setView(holder, notifica);

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        holder.bAggiungiAmicoNotifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }

    private void setView(AdapterVisualizzaNotifiche.ViewHolder holder, Notifica notifica){
        // RAR RAA RFP RLP VLP ARSS AASS ARSP AASP
        if(!(notifica.getTipo().equals("RAR"))) {
            holder.bAggiungiAmicoNotifica.setVisibility(View.GONE);
        }
        if(notifica.getTipo().equals("RAR")) {
            holder.tvTestoNotifica.setText("L'utente " + notifica.getUsernameMittente() + " ti ha inviato una richiesta di amicizia");
        }
        else if(notifica.getTipo().equals("RAA")) {
            holder.tvTestoNotifica.setText("L'utente " + notifica.getUsernameMittente() + " ha accettato la tua richiesta di amicizia");
        }
        else if(notifica.getTipo().equals("RFP")) {
            holder.tvTestoNotifica.setText("L'utente " + notifica.getUsernameMittente() + " ti ha raccomandato un suo film preferito: " + notifica.getTitoloFilmPreferito());
        }
        else if(notifica.getTipo().equals("RLP")) {
            holder.tvTestoNotifica.setText("L'utente " + notifica.getUsernameMittente() + " ti ha raccomandato una sua lista personalizzata: " + notifica.getTitoloLista());
        }
        else if(notifica.getTipo().equals("VLP")) {
            holder.tvTestoNotifica.setText("L'utente " + notifica.getUsernameMittente() + " ha valutato la tua lista personalizzata: " + notifica.getTitoloLista());
        }
        else if(notifica.getTipo().equals("ARSS")) {
            holder.tvTestoNotifica.setText("Un amministratore ha rifiutato la tua segnalazione"); // dire quale segnalazione?
        }
        else if(notifica.getTipo().equals("AASS")) {
            holder.tvTestoNotifica.setText("Un amministratore ha accolto la tua segnalazione"); // dire quale segnalazione?
        }
        else if(notifica.getTipo().equals("ARSP")) {
            holder.tvTestoNotifica.setText("Un amministratore ha rifiutato la tua segnalazione"); // dire quale segnalazione?
        }
        else if(notifica.getTipo().equals("AASP")) {
            holder.tvTestoNotifica.setText("Un amministratore ha accolto la tua segnalazione"); // dire quale segnalazione?
        }

    }

    private static class ViewHolder{
        TextView tvTestoNotifica;
        Button bAggiungiAmicoNotifica;
    }

}
