package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Film;
import com.example.cinemates20.Model.ListaPersonalizzata;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonalizzataDAO {

    private String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public ArrayList<ListaPersonalizzata> prelevaListePersonalizzate(String username){
        ArrayList<ListaPersonalizzata> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaListePersonalizzate.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String titolo = jsonObject.getString("titolo");
                String user = jsonObject.getString("username");
                String descrizione = jsonObject.getString("descrizione");
                arrayList.add(new ListaPersonalizzata(titolo,descrizione,user));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return null;
        }
        return arrayList;
    }

    public boolean aggiungiFilmAListaPers(String username, String titolo, Film film){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("titolo_lista", titolo));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(film.getId())));
        params.add(new BasicNameValuePair("titolo_film", film.getTitolo()));
        params.add(new BasicNameValuePair("dataUscita", film.getDataUscita()));
        params.add(new BasicNameValuePair("posterPath", film.getPathPoster()));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/aggiungiFilmAListaPersonalizzata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error add pref","Impossibile aggiungere film preferito");
            return false;
        }
        return true;
    }

    public boolean creaListaPers(String titolo, String username, String descrizione){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("titolo", titolo));
        params.add(new BasicNameValuePair("descrizione", descrizione));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/creaListaPersonalizzata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error add pref","Impossibile aggiungere film preferito");
            return false;
        }
        return true;
    }

    public ListaPersonalizzata prelevaDescrizioneListaPersonalizzata(String username, String titoloLista){
        ListaPersonalizzata listaPersonalizzata = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaDescrizioneListaPersonalizzata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String descrizione = jsonObject.getString("descrizione");
                listaPersonalizzata = new ListaPersonalizzata(titoloLista,descrizione,username);
            }
        } catch (Throwable e) {
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return null;
        }
        return listaPersonalizzata;
    }

}
