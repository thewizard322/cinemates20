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

import com.example.cinemates20.Presenter.Fragment.AggiungiAmicoPresenter;
import com.example.cinemates20.R;
import com.example.cinemates20.View.Fragment.AggiungiAmicoFragment;

import java.util.ArrayList;

public class AdapterAggiungiAmici extends ArrayAdapter<String> {

    private Context context;
    private AggiungiAmicoFragment aggiungiAmicoFragment;
    private ArrayList<String> arrayList;
    private AggiungiAmicoPresenter aggiungiAmicoPresenter;

    public AdapterAggiungiAmici(@NonNull Context context, AggiungiAmicoFragment aggiungiAmicoFragment, ArrayList<String> arrayList,AggiungiAmicoPresenter aggiungiAmicoPresenter) {
        super(context, R.layout.row_aggiungiamico, arrayList);
        this.arrayList = arrayList;
        this.aggiungiAmicoFragment = aggiungiAmicoFragment;
        this.context = context;
        this.aggiungiAmicoPresenter=aggiungiAmicoPresenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        ViewHolder holder;
        if(rootView == null){
            LayoutInflater inflater = aggiungiAmicoFragment.getActivity().getLayoutInflater();
            rootView = inflater.inflate(R.layout.row_aggiungiamico,null,true);
            holder = new ViewHolder();
            holder.tvUsername = rootView.findViewById(R.id.tvUsername);
            holder.btRichiestaAmicizia= rootView.findViewById(R.id.btRichiestaAmicizia);
            rootView.setTag(holder);
        }
        else{
            holder = (ViewHolder) rootView.getTag();
        }

        String username = arrayList.get(position);
        setView(holder,username);
        holder.btRichiestaAmicizia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aggiungiAmicoPresenter.inviaRichiestaAmicizia(arrayList.get(position));
            }
        });
        return rootView;
    }

    private void setView(ViewHolder holder, String username){
        holder.tvUsername.setText(username);
    }

    private static class ViewHolder{
        TextView tvUsername;
        Button btRichiestaAmicizia;
    }
}
