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
import com.example.cinemates20.Presenter.Fragment.VisualizzaListaPersonalizzataRaccomandataPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.VisualizzaListaPersonalizzataRaccomandataFragment;

import java.util.ArrayList;

public class AdapterVisualizzaListaPersonalizzataRaccomandata extends ArrayAdapter<Film> {
    private Context context;
    private VisualizzaListaPersonalizzataRaccomandataFragment visualizzaListaPersonalizzataRaccomandataFragment;
    private ArrayList<Film> arrayList;
    private VisualizzaListaPersonalizzataRaccomandataPresenter visualizzaListaPersonalizzataRaccomandataPresenter;

    public AdapterVisualizzaListaPersonalizzataRaccomandata(@NonNull Context context, VisualizzaListaPersonalizzataRaccomandataFragment visualizzaListaPersonalizzataRaccomandataFragment, ArrayList<Film> arrayList, VisualizzaListaPersonalizzataRaccomandataPresenter visualizzaListaPersonalizzataRaccomandataPresenter) {
        super(context, R.layout.row_filmvisualizzalistepersonalizzate, arrayList);
        this.arrayList = arrayList;
        this.visualizzaListaPersonalizzataRaccomandataFragment = visualizzaListaPersonalizzataRaccomandataFragment;
        this.context = context;
        this.visualizzaListaPersonalizzataRaccomandataPresenter = visualizzaListaPersonalizzataRaccomandataPresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterVisualizzaListaPersonalizzataRaccomandata.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = visualizzaListaPersonalizzataRaccomandataFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_filmvisualizzalistepersonalizzate,null,true);
            holder = new AdapterVisualizzaListaPersonalizzataRaccomandata.ViewHolder();
            holder.ivRowListePersonalizzate = rootView.findViewById(R.id.ivRowListePersonalizzate);
            holder.tvTitoloRowVisualizzaListePersonalizzate = rootView.findViewById(R.id.tvTitoloRowVisualizzaListePersonalizzate);
            holder.tvAnnoRowVisualizzaListePersonalizzate = rootView.findViewById(R.id.tvAnnoRowVisualizzaListePersonalizzate);
            rootView.setTag(holder);
        }
        else{
            holder = (AdapterVisualizzaListaPersonalizzataRaccomandata.ViewHolder) rootView.getTag();
        }

        Film film = arrayList.get(position);
        setView(holder,film);
        return rootView;
    }

    private void setView(AdapterVisualizzaListaPersonalizzataRaccomandata.ViewHolder holder, Film film){
        holder.tvTitoloRowVisualizzaListePersonalizzate.setText(film.getTitolo());
        holder.tvAnnoRowVisualizzaListePersonalizzate.setText(film.getDataUscita());

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(film.getPatPosterW92()).apply(options).into(holder.ivRowListePersonalizzate);

    }

    private static class ViewHolder{
        ImageView ivRowListePersonalizzate;
        TextView tvTitoloRowVisualizzaListePersonalizzate;
        TextView tvAnnoRowVisualizzaListePersonalizzate;
    }
}
