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
import com.example.cinemates20.Presenter.Fragment.FilmDaVederePresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.FilmDaVedereFragment;

import java.util.ArrayList;

public class AdapterFilmDaVedere extends ArrayAdapter<Film> {
    private Context context;
    private FilmDaVedereFragment filmDaVedereFragment;
    private ArrayList<Film> arrayList;
    private FilmDaVederePresenter filmDaVederePresenter;

    public AdapterFilmDaVedere(@NonNull Context context, FilmDaVedereFragment filmDaVedereFragment, ArrayList<Film> arrayList,FilmDaVederePresenter filmDaVederePresenter) {
        super(context, R.layout.row_filmdavedere, arrayList);
        this.arrayList = arrayList;
        this.filmDaVedereFragment=filmDaVedereFragment;
        this.context = context;
        this.filmDaVederePresenter=filmDaVederePresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterFilmDaVedere.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = filmDaVedereFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_filmdavedere,null,true);
            holder = new AdapterFilmDaVedere.ViewHolder();
            holder.ivRowFilmDaVedere = rootView.findViewById(R.id.ivRowFilmDaVedere);
            holder.tvTitoloRowFilmDaVedere=rootView.findViewById(R.id.tvTitoloRowFilmDaVedere);
            holder.tvAnnoRowFilmDaVedere=rootView.findViewById(R.id.tvAnnoRowFilmDaVedere);
            holder.btRimuoviFilmDaVedere=rootView.findViewById(R.id.btRimuoviFilmDaVedere);
            rootView.setTag(holder);
        }
        else{
            holder = (AdapterFilmDaVedere.ViewHolder) rootView.getTag();
        }

        Film film = arrayList.get(position);
        setView(holder,film);

        holder.btRimuoviFilmDaVedere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmDaVederePresenter.rimuoviFilmDaVedere(arrayList.get(position).getId());
            }
        });

        return rootView;
    }

    private void setView(ViewHolder holder, Film film){
        holder.tvTitoloRowFilmDaVedere.setText(film.getTitolo());
        holder.tvAnnoRowFilmDaVedere.setText(film.getDataUscita());

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.drawable.delete).error(R.drawable.delete);
        Glide.with(context).load(film.getPatPosterW92()).apply(options).into(holder.ivRowFilmDaVedere);
    }

    private static class ViewHolder{
        ImageView ivRowFilmDaVedere;
        TextView tvTitoloRowFilmDaVedere;
        TextView tvAnnoRowFilmDaVedere;
        Button btRimuoviFilmDaVedere;
    }
}

