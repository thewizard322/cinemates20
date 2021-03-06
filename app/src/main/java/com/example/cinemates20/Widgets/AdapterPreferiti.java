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
import com.example.cinemates20.Presenter.Fragment.PreferitiPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.PreferitiFragment;

import java.util.ArrayList;

public class AdapterPreferiti extends ArrayAdapter<Film> {
    private Context context;
    private PreferitiFragment preferitiFragment;
    private ArrayList<Film> arrayList;
    private PreferitiPresenter preferitiPresenter;

    public AdapterPreferiti(@NonNull Context context, PreferitiFragment preferitiFragment, ArrayList<Film> arrayList,PreferitiPresenter preferitiPresenter) {
        super(context, R.layout.row_preferiti, arrayList);
        this.arrayList = arrayList;
        this.preferitiFragment = preferitiFragment;
        this.context = context;
        this.preferitiPresenter=preferitiPresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterPreferiti.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = preferitiFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_preferiti,null,true);
            holder = new AdapterPreferiti.ViewHolder();
            holder.ivRowPreferiti = rootView.findViewById(R.id.ivRowPreferiti);
            holder.tvTitoloRowPreferiti= rootView.findViewById(R.id.tvTitoloRowPreferiti);
            holder.tvAnnoRowPreferiti = rootView.findViewById(R.id.tvAnnoRowPreferiti);
            holder.btRimuoviDaPreferiti = rootView.findViewById(R.id.btRimuoviDaPreferiti);
            holder.btCondividiPreferiti = rootView.findViewById(R.id.btCondividiPreferiti);
            rootView.setTag(holder);
        }
        else{
            holder = (AdapterPreferiti.ViewHolder) rootView.getTag();
        }

        Film film = arrayList.get(position);
        setView(holder,film);

        holder.btRimuoviDaPreferiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferitiPresenter.rimuoviDaPreferiti(arrayList.get(position).getId());
            }
        });
        holder.btCondividiPreferiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferitiPresenter.addRaccomandaPreferitoFragment(arrayList.get(position));
            }
        });

        return rootView;
    }

    private void setView(ViewHolder holder, Film film){
        holder.tvTitoloRowPreferiti.setText(film.getTitolo());
        holder.tvAnnoRowPreferiti.setText(film.getDataUscita());

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.drawable.delete).error(R.drawable.delete);
        Glide.with(context).load(film.getPatPosterW92()).apply(options).into(holder.ivRowPreferiti);
    }

    private static class ViewHolder{
        ImageView ivRowPreferiti;
        TextView tvTitoloRowPreferiti;
        TextView tvAnnoRowPreferiti;
        Button btRimuoviDaPreferiti;
        Button btCondividiPreferiti;
    }
}

