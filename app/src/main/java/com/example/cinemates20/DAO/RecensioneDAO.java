package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Recensione;

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

public class RecensioneDAO {

    private String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public ArrayList<Recensione> prelevaRecensioniFilm(int idFilm){
        ArrayList<Recensione> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id_film", String.valueOf(idFilm)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaRecensioniFilm.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idRecensione = jsonObject.getInt("id_recensione");
                int idF = jsonObject.getInt("id_film");
                int valutazione = jsonObject.getInt("valutazione");
                String testo = jsonObject.getString("testo");
                String username = jsonObject.getString("username");
                arrayList.add(new Recensione(idRecensione,idF,username,testo,valutazione));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo listPers","Impossibile prelevare film dalla lista personalizzata");
            return null;
        }
        return arrayList;
    }

    public boolean inserisciRecensione(String username, int idFilm, String testo, String voto){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("id_film", String.valueOf(idFilm)));
        params.add(new BasicNameValuePair("testo", testo));
        params.add(new BasicNameValuePair("valutazione", voto));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/inserisciRecensione.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error login user","Impossibile loggare utente");
            return false;
        }
        return true;
    }

    public boolean segnalaRecensione(String username, int idRecensione){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("id_recensione", String.valueOf(idRecensione)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/segnalaRecensione.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error login user","Impossibile loggare utente");
            return false;
        }
        return true;
    }

}
