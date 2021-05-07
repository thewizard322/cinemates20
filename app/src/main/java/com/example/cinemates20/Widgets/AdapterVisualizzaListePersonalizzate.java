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
import com.example.cinemates20.Presenter.Fragment.VisualizzaListePersonalizzatePresenter;
import com.example.cinemates20.View.Fragment.VisualizzaListePersonalizzateFragment;
import com.example.cinemates20.R;

import java.util.ArrayList;

public class AdapterVisualizzaListePersonalizzate extends ArrayAdapter<Film> {
    private Context context;
    private VisualizzaListePersonalizzateFragment visualizzaListePersonalizzateFragment;
    private ArrayList<Film> arrayList;
    private VisualizzaListePersonalizzatePresenter visualizzaListePersonalizzatePresenter;

    public AdapterVisualizzaListePersonalizzate(@NonNull Context context, VisualizzaListePersonalizzateFragment visualizzaListePersonalizzateFragment, ArrayList<Film> arrayList, VisualizzaListePersonalizzatePresenter visualizzaListePersonalizzatePresenter) {
        super(context, R.layout.row_filmvisualizzalistepersonalizzate, arrayList);
        this.arrayList = arrayList;
        this.visualizzaListePersonalizzateFragment = visualizzaListePersonalizzateFragment;
        this.context = context;
        this.visualizzaListePersonalizzatePresenter = visualizzaListePersonalizzatePresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterVisualizzaListePersonalizzate.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = visualizzaListePersonalizzateFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_filmvisualizzalistepersonalizzate,null,true);
            holder = new AdapterVisualizzaListePersonalizzate.ViewHolder();
            holder.ivRowListePersonalizzate = rootView.findViewById(R.id.ivRowListePersonalizzate);
            holder.tvTitoloRowVisualizzaListePersonalizzate = rootView.findViewById(R.id.tvTitoloRowVisualizzaListePersonalizzate);
            holder.tvAnnoRowVisualizzaListePersonalizzate = rootView.findViewById(R.id.tvAnnoRowVisualizzaListePersonalizzate);
            rootView.setTag(holder);
        }
        else{
            holder = (ViewHolder) rootView.getTag();
        }

        holder.tvTitoloRowVisualizzaListePersonalizzate.setText(arrayList.get(position).getTitolo());
        holder.tvAnnoRowVisualizzaListePersonalizzate.setText(arrayList.get(position).getDataUscita());

        RequestOptions options = new RequestOptions()
                .centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(arrayList.get(position).getPatPosterW92()).apply(options).into(holder.ivRowListePersonalizzate);

        return rootView;
    }

    private static class ViewHolder{
        ImageView ivRowListePersonalizzate;
        TextView tvTitoloRowVisualizzaListePersonalizzate;
        TextView tvAnnoRowVisualizzaListePersonalizzate;
    }

}