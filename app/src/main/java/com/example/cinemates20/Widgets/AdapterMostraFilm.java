package com.example.cinemates20.Widgets;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
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
import com.example.cinemates20.View.Fragment.RicercaFilmFragment;

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
        if(rowView == null){
            if(position==0){
                LayoutInflater inflater = mostraFilmFragment.getLayoutInflater();
                rowView = inflater.inflate(R.layout.row_mostrafilm,null,true);}
            else{
                LayoutInflater inflater = mostraFilmFragment.getLayoutInflater();
                rowView = inflater.inflate(R.layout.row_recensione,null,true); }
        }

        if(position == 0){
            ImageView iwMostraFilm = rowView.findViewById(R.id.iwMostraFilm);
            TextView tvTitoloMostraFilm = rowView.findViewById(R.id.tvTitoloMostraFilm);
            TextView tvData = rowView.findViewById(R.id.tvDataMostraFilm);
            TextView tvGeneriMostraFilm = rowView.findViewById(R.id.tvGeneriMostraFilm);
            TextView tvAttoriMostraFilm = rowView.findViewById(R.id.tvAttoriMostraFilm);
            TextView tvRegistaMostraFilm = rowView.findViewById(R.id.tvRegistaMostraFilm);
            TextView tvTramaMostraFilm = rowView.findViewById(R.id.tvTramaMostraFilm);
            tvTitoloMostraFilm.setText(film.getTitolo());
            tvData.setText(film.getDataUscita());
            tvGeneriMostraFilm.setText(film.getGenere().toString());
            tvAttoriMostraFilm.setText(film.getAttori().toString());
            tvRegistaMostraFilm.setText(film.getRegista());
            tvTramaMostraFilm.setText(film.getTrama());
            RequestOptions options = new RequestOptions()
                    .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(film.getPatPosterW154()).apply(options).into(iwMostraFilm);
        }
        return rowView;
    }
}
