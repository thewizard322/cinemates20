package com.example.cinemates20.Widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Recensione;
import com.example.cinemates20.Presenter.Fragment.MostraFilmPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;

import java.util.ArrayList;

public class AdapterMostraFilm extends ArrayAdapter<Recensione> {

    private Context context;
    private MostraFilmFragment mostraFilmFragment;
    private MostraFilmPresenter mostraFilmPresenter;
    private ArrayList<Recensione> arrayList;
    private Film film;

    public AdapterMostraFilm(@NonNull Context context, MostraFilmFragment mostraFilmFragment,
                             MostraFilmPresenter mostraFilmPresenter, ArrayList<Recensione> arrayList, Film film) {
        super(context, R.layout.row_recensione, arrayList);
        this.arrayList = arrayList;
        this.mostraFilmFragment = mostraFilmFragment;
        this.mostraFilmPresenter = mostraFilmPresenter;
        this.film = film;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size()+1;
    }

    @Override
    public Recensione getItem(int position) {
        return arrayList.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return position-1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return 0;
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ViewHolderFilm holderFilm;
        ViewHolderRecensione holderRecensione;

        if(rowView == null){
            LayoutInflater inflater = mostraFilmFragment.getLayoutInflater();
            if(position==0){
                rowView = inflater.inflate(R.layout.row_mostrafilm,null,true);
                holderFilm = new ViewHolderFilm();
                holderFilm.iwMostraFilm = rowView.findViewById(R.id.iwMostraFilm);
                holderFilm.tvTitoloMostraFilm = rowView.findViewById(R.id.tvTitoloMostraFilm);
                holderFilm.tvData = rowView.findViewById(R.id.tvDataMostraFilm);
                holderFilm.tvGeneriMostraFilm = rowView.findViewById(R.id.tvGeneriMostraFilm);
                holderFilm.tvAttoriMostraFilm = rowView.findViewById(R.id.tvAttoriMostraFilm);
                holderFilm.tvRegistaMostraFilm = rowView.findViewById(R.id.tvRegistaMostraFilm);
                holderFilm.tvTramaMostraFilm = rowView.findViewById(R.id.tvTramaMostraFilm);
                holderFilm.bPreferitiMostraFilm = rowView.findViewById(R.id.bPreferitiMostraFilm);
                holderFilm.bFilmDaVedereMostraFilm = rowView.findViewById(R.id.bFilmDaVedereMostraFilm);
                holderFilm.bRecensisciMostraFilm = rowView.findViewById(R.id.bRecensisciMostraFilm);
                rowView.setTag(holderFilm);
            }
            else{
                rowView = inflater.inflate(R.layout.row_recensione,null,true);
                holderRecensione = new ViewHolderRecensione();
                holderRecensione.tvUtenteRecensione = rowView.findViewById(R.id.tvUtenteRecensione);
                holderRecensione.tvTestoRecensione = rowView.findViewById(R.id.tvTestoRecensione);
                holderRecensione.tvValutazioneRecensione = rowView.findViewById(R.id.tvValutazioneRecensione);
                holderRecensione.bSegnalaRecenesione = rowView.findViewById(R.id.bSegnalaRecenesione);
                rowView.setTag(holderRecensione);
            }
        }

        if(position == 0){
            holderFilm = (ViewHolderFilm) rowView.getTag();
            holderFilm.tvTitoloMostraFilm.setText(film.getTitolo());
            holderFilm.tvData.setText("Data uscita: "+film.getDataUscita());
            holderFilm.tvGeneriMostraFilm.setText(film.getGenere().toString());
            holderFilm.tvAttoriMostraFilm.setText(film.getAttori().toString());
            holderFilm.tvRegistaMostraFilm.setText("Regia di "+film.getRegista());
            holderFilm.tvTramaMostraFilm.setText(film.getTrama());
            RequestOptions options = new RequestOptions()
                    .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(film.getPatPosterW154()).apply(options).into(holderFilm.iwMostraFilm);

            holderFilm.bPreferitiMostraFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraFilmPresenter.aggiungiAiPreferiti();
                }
            });

            holderFilm.bFilmDaVedereMostraFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraFilmPresenter.aggiungiAiFilmDaVedere();
                }
            });

            holderFilm.bRecensisciMostraFilm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraFilmPresenter.inserisciRecensione();
                }
            });
        }
        else{
            holderRecensione = (ViewHolderRecensione) rowView.getTag();
            holderRecensione.tvUtenteRecensione.setText(arrayList.get(position-1).getUsername());
            holderRecensione.tvTestoRecensione.setText(arrayList.get(position-1).getTesto());
            holderRecensione.tvValutazioneRecensione.setText("Voto: "+ arrayList.get(position-1).getValutazione());
            holderRecensione.bSegnalaRecenesione.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostraFilmFragment.mostraToast("Ho cliccato segnala su" + arrayList.get(position-1).getUsername());
                }
            });
        }
        return rowView;
    }

    private static class ViewHolderFilm{
        ImageView iwMostraFilm;
        TextView tvTitoloMostraFilm;
        TextView tvData;
        TextView tvGeneriMostraFilm;
        TextView tvAttoriMostraFilm;
        TextView tvRegistaMostraFilm;
        TextView tvTramaMostraFilm;
        Button bPreferitiMostraFilm;
        Button bFilmDaVedereMostraFilm;
        Button bRecensisciMostraFilm;
    }

    private static class ViewHolderRecensione{
        TextView tvUtenteRecensione;
        TextView tvTestoRecensione;
        TextView tvValutazioneRecensione;
        Button bSegnalaRecenesione;
    }

}
