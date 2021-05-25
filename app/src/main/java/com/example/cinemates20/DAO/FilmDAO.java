package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Film;

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

public class FilmDAO {

    private String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public boolean aggiungiAiPreferiti(String username, Film film){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(film.getId())));
        params.add(new BasicNameValuePair("titolo", film.getTitolo()));
        params.add(new BasicNameValuePair("dataUscita", film.getDataUscita()));
        params.add(new BasicNameValuePair("posterPath", film.getPathPoster()));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/aggiungiAiPreferiti.php");
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

    public boolean aggiungiAiFilmDaVedere(String username, Film film){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(film.getId())));
        params.add(new BasicNameValuePair("titolo", film.getTitolo()));
        params.add(new BasicNameValuePair("dataUscita", film.getDataUscita()));
        params.add(new BasicNameValuePair("posterPath", film.getPathPoster()));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/aggiungiAiFilmDaVedere.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error add film da veder","Impossibile aggiungere film da vedere");
            return false;
        }
        return true;
    }

    public boolean eliminaDaPreferiti(String username, int idFilm){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(idFilm)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/eliminaDaPreferiti.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error add pref","Impossibile eliminare film preferito");
            return false;
        }
        return true;
    }

    public ArrayList<Film> prelevaPreferiti(String username){
        ArrayList<Film> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaPreferiti.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idFilm = jsonObject.getInt("id_film");
                String titolo = jsonObject.getString("titolo");
                String dataUscita = jsonObject.getString("anno");
                String posterPath = jsonObject.getString("posterpath");
                arrayList.add(new Film(idFilm,titolo,dataUscita,posterPath));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return null;
        }
        return arrayList;
    }

    public ArrayList<Film> prelevaFilmDaVedere(String username){
        ArrayList<Film> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaFilmDaVedere.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idFilm = jsonObject.getInt("id_film");
                String titolo = jsonObject.getString("titolo");
                String dataUscita = jsonObject.getString("anno");
                String posterPath = jsonObject.getString("posterpath");
                arrayList.add(new Film(idFilm,titolo,dataUscita,posterPath));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo FDV","Impossibile prelevare i film da vedere");
            return null;
        }
        return arrayList;
    }

    public boolean eliminaFilmDaVedere(String username, int idFilm){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(idFilm)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/eliminaFilmDaVedere.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error add pref","Impossibile eliminare film da vedere");
            return false;
        }
        return true;
    }

    public ArrayList<Film> prelevafilmListaPersonalizzata(String username, String titoloLista){
        ArrayList<Film> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevafilmListaPersonalizzata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idFilm = jsonObject.getInt("id_film");
                String titolo = jsonObject.getString("titolo_film");
                String dataUscita = jsonObject.getString("anno");
                String posterPath = jsonObject.getString("posterpath");
                arrayList.add(new Film(idFilm,titolo,dataUscita,posterPath));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo listPers","Impossibile prelevare film dalla lista personalizzata");
            return null;
        }
        return arrayList;
    }

}
