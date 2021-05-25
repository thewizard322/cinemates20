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
        /*boolean isCon = connect();
        if(isCon==false)
            return null;
        String query = "SELECT id_recensione, id_film, username, testo, valutazione FROM recensione WHERE id_film = ? " +
                "ORDER BY id_recensione DESC";
        ArrayList<Recensione> recensioneList = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idRecensione = rs.getInt("id_recensione");
                int idF = rs.getInt("id_film");
                String username = rs.getString("username");
                String testo = rs.getString("testo");
                int valutazione = rs.getInt("valutazione");
                Recensione recensione = new Recensione(idRecensione,idF,username,testo,valutazione);
                recensioneList.add(recensione);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error query","Errore query");
            return null;
        }
        finally {
            closeConnection();
        }
        return recensioneList;*/
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
        /*boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO recensione (id_film,username,testo,valutazione) VALUES (?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            st.setString(2, username);
            st.setString(3,testo);
            st.setString(4,voto);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error insert recensione","Impossibile inserire recensione");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;*/
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
        /*boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO segnalazione (id_recensione,username) VALUES (?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idRecensione);
            st.setString(2, username);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error segnala review","Impossibile segnalare recensione");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;*/
    }

}
