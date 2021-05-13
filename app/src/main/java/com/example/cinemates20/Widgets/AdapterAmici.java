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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.Utente;
import com.example.cinemates20.Presenter.Fragment.AmiciPresenter;
import com.example.cinemates20.Presenter.Fragment.VisualizzaListePersonalizzateAmiciPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AmiciFragment;
import com.example.cinemates20.View.Fragment.VisualizzaListePersonalizzateAmiciFragment;

import java.util.ArrayList;

public class AdapterAmici extends ArrayAdapter<String> {
    private Context context;
    private AmiciFragment amiciFragment;
    private ArrayList<String> arrayList;
    private AmiciPresenter amiciPresenter;

    public AdapterAmici(@NonNull Context context, AmiciFragment amiciFragment, ArrayList<String> arrayList,AmiciPresenter amiciPresenter) {
        super(context, R.layout.row_amici, arrayList);
        this.arrayList = arrayList;
        this.amiciFragment = amiciFragment;
        this.context = context;
        this.amiciPresenter=amiciPresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        AdapterAmici.ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = amiciFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_amici,null,true);
            holder = new AdapterAmici.ViewHolder();
            holder.tvAmici = rootView.findViewById(R.id.tvAmici);
            holder.btVisualizzaListeAmico = rootView.findViewById(R.id.btVisualizzaListeAmico);
        }
        else{
            holder = (AdapterAmici.ViewHolder) rootView.getTag();
        }

        String username = arrayList.get(position);
        setView(holder,username);

        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        holder.btVisualizzaListeAmico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             amiciPresenter.replaceVisualizzaListePersonalizzateAmiciFragment(username);
            }
        });
        return rootView;
    }
    private void setView(ViewHolder holder, String username){
        holder.tvAmici.setText(username);
        RequestOptions options = new RequestOptions();
                options.centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
    }

    private static class ViewHolder{
        TextView tvAmici;
        Button btVisualizzaListeAmico;
    }
}

