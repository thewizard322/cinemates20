package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Notifica;

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

public class NotificaDAO {

    private String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

    public boolean inviaNotificaValutazioneListaAmico(String usernameMittente, String usernameDestinatario,
                                              String titoloLista, int likeOrDislike, String commento){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        params.add(new BasicNameValuePair("like_or_dislike", String.valueOf(likeOrDislike)));
        params.add(new BasicNameValuePair("commento", commento));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/inviaNotificaValutazioneListaAmico.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error register user","Impossibile registrare utente");
            return false;
        }
        return true;
    }

    public boolean checkValutazioneGiaInviata(String usernameMittente, String usernameDestinatario,
                                              String titoloLista){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkValutazioneGiaInviata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count!=0)
                return false;
        } catch (Throwable e) {
            Log.e("Error check email","Impossibile verificare l'email");
            return false;
        }
        return true;
    }

    public boolean checkPreferitoGiaRaccomandato(String usernameMittente, String usernameDestinatario
                                    , int idFilm){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("id_film_preferito", String.valueOf(idFilm)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkPreferitoGiaRaccomandato.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count!=0)
                return false;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean checkListaGiaRaccomandata(String usernameMittente, String usernameDestinatario
            , String titoloLista){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkListaGiaRaccomandata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count!=0)
                return false;
        } catch (Throwable e) {
            Log.e("Error check email","Impossibile verificare l'email");
            return false;
        }
        return true;
    }

    public boolean raccomandaPreferito(String usernameMittente, String usernameDestinatario
            , int idFilm, String titoloFilm, String dataUscita, String posterPath){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("id_film", String.valueOf(idFilm)));
        params.add(new BasicNameValuePair("titolo_film", titoloFilm));
        params.add(new BasicNameValuePair("data_uscita", dataUscita));
        params.add(new BasicNameValuePair("poster_path", posterPath));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/raccomandaPreferito.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error register user","Impossibile registrare utente");
            return false;
        }
        return true;
    }

    public boolean raccomandaLista(String usernameMittente, String usernameDestinatario
            , String titoloLista){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/raccomandaLista.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error register user","Impossibile registrare utente");
            return false;
        }
        return true;
    }

    public ArrayList<Notifica> prelevaNotifiche(String username) {
        ArrayList<Notifica> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaNotifiche.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idNotifica = jsonObject.getInt("id_notifica");
                String usernameMittente = jsonObject.getString("username_mittente");
                String usernameDestinatario = jsonObject.getString("username_destinatario");
                String tipo = jsonObject.getString("tipologia");
                int idFilmPreferito = jsonObject.optInt("id_film_preferito",0);
                String titoloFilmPreferito = jsonObject.getString("titolo_film_preferito");
                String dataUscitaPreferito = jsonObject.getString("data_film_preferito");
                String posterPathPreferito = jsonObject.getString("posterpath_film_preferto");
                String titoloLista = jsonObject.getString("titolo_lista");
                String usernamelista = jsonObject.getString("username_lista");
                int likeOrDislike = jsonObject.optInt("like_dislike",2);
                String commento = jsonObject.getString("commento");

                Notifica notifica = new Notifica(idNotifica,usernameMittente,usernameDestinatario,tipo);
                notifica.setIdFilmPreferito(idFilmPreferito);
                notifica.setDataUscitaPreferito(dataUscitaPreferito);
                notifica.setPosterPathPreferito(posterPathPreferito);
                notifica.setTitoloFilmPreferito(titoloFilmPreferito);
                notifica.setTitoloLista(titoloLista);
                notifica.setCommento(commento);
                notifica.setLikeOrDislike(likeOrDislike);

                arrayList.add(notifica);
            }
        } catch (Throwable e) {
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return null;
        }
        return arrayList;
    }

    public boolean inviaNotificaAmicizia(String usernameMittente, String usernameDestinatario, String tipologia) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("tipo", tipologia));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/inviaNotificaAmicizia.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return false;
        } catch (Throwable e) {
            Log.e("Error register user","Impossibile registrare utente");
            return false;
        }
        return true;
    }

    public boolean rimuoviNotifica(String usernameMittente, String usernameDestinatario, String tipo){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", usernameMittente));
        params.add(new BasicNameValuePair("username_destinatario", usernameDestinatario));
        params.add(new BasicNameValuePair("tipo", tipo));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/rimuoviNotifica.php");
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

}
