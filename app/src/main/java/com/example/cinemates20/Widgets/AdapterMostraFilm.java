package com.example.cinemates20.Widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Recensione;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.MostraFilmFragment;

import java.util.ArrayList;

public class AdapterMostraFilm extends ArrayAdapter<Recensione> {

    private Context context;
    private MostraFilmFragment mostraFilmFragment;
    private ArrayList<Recensione> arrayList;
    private Film film;

    public AdapterMostraFilm(@NonNull Context context, MostraFilmFragment mostraFilmFragment,
                              ArrayList<Recensione> arrayList, Film film) {
        super(context, R.layout.row_recensione, arrayList);
        this.arrayList = arrayList;
        this.mostraFilmFragment = mostraFilmFragment;
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
            if(position==0){
                LayoutInflater inflater = mostraFilmFragment.getLayoutInflater();
                rowView = inflater.inflate(R.layout.row_mostrafilm,null,true);
                holderFilm = new ViewHolderFilm();
                holderFilm.iwMostraFilm = rowView.findViewById(R.id.iwMostraFilm);
                holderFilm.tvTitoloMostraFilm = rowView.findViewById(R.id.tvTitoloMostraFilm);
                holderFilm.tvData = rowView.findViewById(R.id.tvDataMostraFilm);
                holderFilm.tvGeneriMostraFilm = rowView.findViewById(R.id.tvGeneriMostraFilm);
                holderFilm.tvAttoriMostraFilm = rowView.findViewById(R.id.tvAttoriMostraFilm);
                holderFilm.tvRegistaMostraFilm = rowView.findViewById(R.id.tvRegistaMostraFilm);
                holderFilm.tvTramaMostraFilm = rowView.findViewById(R.id.tvTramaMostraFilm);
                rowView.setTag(holderFilm);
            }
            else{
                LayoutInflater inflater = mostraFilmFragment.getLayoutInflater();
                rowView = inflater.inflate(R.layout.row_recensione,null,true);
                holderRecensione = new ViewHolderRecensione();
                holderRecensione.tvUtenteRecensione = rowView.findViewById(R.id.tvUtenteRecensione);
                holderRecensione.tvTestoRecensione = rowView.findViewById(R.id.tvTestoRecensione);
                holderRecensione.tvValutazioneRecensione = rowView.findViewById(R.id.tvValutazioneRecensione);
                rowView.setTag(holderRecensione);
            }
        }

        if(position == 0){
            holderFilm = (ViewHolderFilm) rowView.getTag();
            holderFilm.tvTitoloMostraFilm.setText(film.getTitolo());
            holderFilm.tvData.setText(film.getDataUscita());
            holderFilm.tvGeneriMostraFilm.setText(film.getGenere().toString());
            holderFilm.tvAttoriMostraFilm.setText(film.getAttori().toString());
            holderFilm.tvRegistaMostraFilm.setText(film.getRegista());
            holderFilm.tvTramaMostraFilm.setText(film.getTrama());
            RequestOptions options = new RequestOptions()
                    .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(film.getPatPosterW154()).apply(options).into(holderFilm.iwMostraFilm);
        }
        else{
            holderRecensione = (ViewHolderRecensione) rowView.getTag();
            holderRecensione.tvUtenteRecensione.setText(arrayList.get(position-1).getUsername());
            holderRecensione.tvTestoRecensione.setText(arrayList.get(position-1).getTesto());
            holderRecensione.tvValutazioneRecensione.setText("Voto: "+ arrayList.get(position-1).getValutazione());
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
    }

    private static class ViewHolderRecensione{
        TextView tvUtenteRecensione;
        TextView tvTestoRecensione;
        TextView tvValutazioneRecensione;
    }

}
