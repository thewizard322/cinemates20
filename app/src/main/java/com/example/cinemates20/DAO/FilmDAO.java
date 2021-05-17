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
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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
        /*boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_preferito (id_film,username,titolo,anno,posterpath) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, film.getId());
            st.setString(2, username);
            st.setString(3,film.getTitolo());
            st.setString(4,film.getDataUscita());
            st.setString(5,film.getPathPoster());
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add pref","Impossibile aggiungere film preferito");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;*/
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
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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
        /*boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_da_vedere (id_film,username,titolo,anno,posterpath) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, film.getId());
            st.setString(2, username);
            st.setString(3,film.getTitolo());
            st.setString(4,film.getDataUscita());
            st.setString(5,film.getPathPoster());
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add film da veder","Impossibile aggiungere film da vedere");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;*/
    }

    public boolean eliminaDaPreferiti(String username, int idFilm){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(idFilm)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/eliminaDaPreferiti.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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
        /*boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "DELETE FROM film_preferito WHERE id_film=? AND username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            st.setString(2, username);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add pref","Impossibile eliminare film preferito");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;*/
    }

    public ArrayList<Film> prelevaPreferiti(String username){
        ArrayList<Film> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaPreferiti.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int idFilm = 0;
            String titolo = null;
            String dataUscita = null;
            String posterPath = null;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idFilm = jsonObject.getInt("id_film");
                titolo = jsonObject.getString("titolo");
                dataUscita = jsonObject.getString("anno");
                posterPath = jsonObject.getString("posterpath");
                arrayList.add(new Film(idFilm,titolo,dataUscita,posterPath));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return null;
        }
        return arrayList;
        /*ArrayList<Film> list = new ArrayList<Film>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film,titolo,anno,posterpath FROM film_preferito WHERE username=? ORDER BY titolo ASC";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String titolo = rs.getString("titolo");
                String data = rs.getString("anno");
                String posterPath = rs.getString("posterpath");
                Film film = new Film(idF,titolo,data,posterPath);
                list.add(film);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return list;
        }
        finally {
            closeConnection();
        }
        return list;*/
    }

    public ArrayList<Film> prelevaFilmDaVedere(String username){
        ArrayList<Film> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaFilmDaVedere.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int idFilm = 0;
            String titolo = null;
            String dataUscita = null;
            String posterPath = null;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idFilm = jsonObject.getInt("id_film");
                titolo = jsonObject.getString("titolo");
                dataUscita = jsonObject.getString("anno");
                posterPath = jsonObject.getString("posterpath");
                arrayList.add(new Film(idFilm,titolo,dataUscita,posterPath));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo FDV","Impossibile prelevare i film da vedere");
            return null;
        }
        return arrayList;
        /*ArrayList<Film> list = new ArrayList<Film>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film,titolo,anno,posterpath FROM film_da_vedere WHERE username=? ORDER BY titolo ASC";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String titolo = rs.getString("titolo");
                String data = rs.getString("anno");
                String posterPath = rs.getString("posterpath");
                Film film = new Film(idF,titolo,data,posterPath);
                list.add(film);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo FDV","Impossibile prelevare i film da vedere");
            return list;
        }
        finally {
            closeConnection();
        }
        return list;*/
    }

    public boolean eliminaFilmDaVedere(String username, int idFilm){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("idFilm", String.valueOf(idFilm)));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/eliminaFilmDaVedere.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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
        /*boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "DELETE FROM film_da_vedere WHERE id_film=? AND username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            st.setString(2, username);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add pref","Impossibile eliminare film da vedere");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;*/
    }

    public ArrayList<Film> prelevafilmListaPersonalizzata(String username, String titoloLista){
        ArrayList<Film> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("titolo_lista", titoloLista));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevafilmListaPersonalizzata.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int idFilm = 0;
            String titolo = null;
            String dataUscita = null;
            String posterPath = null;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idFilm = jsonObject.getInt("id_film");
                titolo = jsonObject.getString("titolo_film");
                dataUscita = jsonObject.getString("anno");
                posterPath = jsonObject.getString("posterpath");
                arrayList.add(new Film(idFilm,titolo,dataUscita,posterPath));
            }
        } catch (Throwable e) {
            Log.e("Error prelievo listPers","Impossibile prelevare film dalla lista personalizzata");
            return null;
        }
        return arrayList;
        /*ArrayList<Film> list = new ArrayList<>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film,titolo_film,anno,posterpath FROM film_lista_personalizzata " +
                "WHERE username=? AND titolo_lista=? ORDER BY titolo_film ASC";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, titoloLista);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String titolo = rs.getString("titolo_film");
                String data = rs.getString("anno");
                String posterPath = rs.getString("posterpath");
                Film film = new Film(idF,titolo,data,posterPath);
                list.add(film);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo listPers","Impossibile prelevare film dalla lista personalizzata");
            return list;
        }
        finally {
            closeConnection();
        }
        return list;*/
    }

}
