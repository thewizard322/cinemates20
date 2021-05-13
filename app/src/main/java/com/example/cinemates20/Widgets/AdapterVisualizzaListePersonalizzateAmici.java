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
import com.example.cinemates20.Presenter.Fragment.VisualizzaListePersonalizzateAmiciPresenter;
import com.example.cinemates20.View.Fragment.VisualizzaListePersonalizzateAmiciFragment;
import com.example.cinemates20.R;

import java.util.ArrayList;

public class AdapterVisualizzaListePersonalizzateAmici extends ArrayAdapter<Film> {
    private Context context;
    private VisualizzaListePersonalizzateAmiciFragment visualizzaListePersonalizzateAmiciFragment;
    private ArrayList<Film> arrayList;
    private VisualizzaListePersonalizzateAmiciPresenter visualizzaListePersonalizzateAmiciPresenter;

    public AdapterVisualizzaListePersonalizzateAmici(@NonNull Context context, VisualizzaListePersonalizzateAmiciFragment visualizzaListePersonalizzateAmiciFragment, ArrayList<Film> arrayList, VisualizzaListePersonalizzateAmiciPresenter visualizzaListePersonalizzateAmiciPresenter) {
        super(context, R.layout.row_visualizzalistepersonalizzateamici, arrayList);
        this.arrayList = arrayList;
        this.visualizzaListePersonalizzateAmiciFragment = visualizzaListePersonalizzateAmiciFragment;
        this.context = context;
        this.visualizzaListePersonalizzateAmiciPresenter = visualizzaListePersonalizzateAmiciPresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterVisualizzaListePersonalizzateAmici.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = visualizzaListePersonalizzateAmiciFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_visualizzalistepersonalizzateamici,null,true);
            holder = new AdapterVisualizzaListePersonalizzateAmici.ViewHolder();
            holder.ivRowListePersonalizzateAmico = rootView.findViewById(R.id.ivRowListePersonalizzateAmici);
            holder.tvTitoloRowVisualizzaListePersonalizzateAmico = rootView.findViewById(R.id.tvTitoloRowVisualizzaListePersonalizzateAmici);
            holder.tvAnnoRowVisualizzaListePersonalizzateAmico = rootView.findViewById(R.id.tvAnnoRowVisualizzaListePersonalizzateAmici);
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
        holder.tvTitoloRowVisualizzaListePersonalizzateAmico.setText(film.getTitolo());
        holder.tvAnnoRowVisualizzaListePersonalizzateAmico.setText(film.getDataUscita());

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(film.getPatPosterW92()).apply(options).into(holder.ivRowListePersonalizzateAmico);

    }

    private static class ViewHolder{
        ImageView ivRowListePersonalizzateAmico;
        TextView tvTitoloRowVisualizzaListePersonalizzateAmico;
        TextView tvAnnoRowVisualizzaListePersonalizzateAmico;
    }

}