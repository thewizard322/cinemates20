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
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.RicercaFilmFragment;

import java.util.ArrayList;

public class AdapterRicercaFilm extends ArrayAdapter<Film> {

    private Context context;
    private RicercaFilmFragment ricercaFilmFragment;
    private ArrayList<Film> arrayList;

    public AdapterRicercaFilm(@NonNull Context context, RicercaFilmFragment ricercaFilmFragment, ArrayList<Film> arrayList) {
        super(context, R.layout.row_ricercafilm, arrayList);
        this.arrayList = arrayList;
        this.ricercaFilmFragment = ricercaFilmFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = ricercaFilmFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_ricercafilm,null,true);
            holder = new ViewHolder();
            holder.ivRowRicercaFilm = rootView.findViewById(R.id.ivRowRicercaFilm);
            holder.tvTitoloRowRicercaFilm = rootView.findViewById(R.id.tvTitoloRowRicercaFilm);
            holder.tvAnnoRowRicercaFilm = rootView.findViewById(R.id.tvAnnoRowRicercaFilm);
            rootView.setTag(holder);
        }
        else{
            holder = (ViewHolder) rootView.getTag();
        }

        Film film = arrayList.get(position);
        setView(holder,film);

        return rootView;
    }

    private void setView(ViewHolder holder, Film film){
        holder.tvTitoloRowRicercaFilm.setText(film.getTitolo());
        holder.tvAnnoRowRicercaFilm.setText(film.getDataUscita());


        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(film.getPatPosterW92()).apply(options).into(holder.ivRowRicercaFilm);
    }

    private static class ViewHolder{
        ImageView ivRowRicercaFilm;
        TextView tvTitoloRowRicercaFilm;
        TextView tvAnnoRowRicercaFilm;
    }
}
