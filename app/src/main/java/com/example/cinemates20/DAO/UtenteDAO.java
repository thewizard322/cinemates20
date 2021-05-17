package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Utente;

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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    private String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";
    private Connection con;

    private boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public int registerUser(String username, String pwd, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("email", email));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/registrazione.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            int check = jsonObject.getInt("response");
            if(check != 0)
                return 2;
        } catch (Throwable e) {
            Log.e("Error register user","Impossibile registrare utente");
            return 1;
        }
        return 0;

        /*boolean isCon = connect();
        if(isCon==false)
            return 2;
        String query = "INSERT INTO utente (username,password,email) VALUES (?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, pwd);
            st.setString(3, email);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error register user","Impossibile registrare utente");
            return 1;
        }
        finally {
            closeConnection();
        }
        return 0;*/
    }

    public int login(String username, String password){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/login.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            int count = 0;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                count = jsonObject.getInt("count");
            }
            if(count==0)
                return 1;
        } catch (Throwable e) {
            Log.e("Error login user","Impossibile loggare utente");
            return 2;
        }
        return 0;
        /*
        boolean isCon = connect();
        if(isCon==false)
            return 3;
        String query = "SELECT COUNT(*) AS count FROM utente WHERE username= BINARY ? AND password= BINARY ?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");
                if(count==0)
                    return 1;
            }
        } catch (SQLException throwables) {
            Log.e("Error login user","Impossibile loggare utente");
            return 2;
        }
        finally {
            closeConnection();
        }

        return 0;*/
    }

    public Utente prelevaUtente(String username){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaUtente.php");
        Utente utente;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            String email = null;
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                email = jsonObject.getString("email");
            }
            utente = new Utente(username,email);
        } catch (Throwable e) {
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        return utente;
        /*boolean isCon = connect();
        if(isCon==false)
            return null;
        Utente utente = null;
        String query = "SELECT email FROM utente WHERE username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            rs.next();
            String email = rs.getString("email");
            utente = new Utente(username,email);
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        finally {
            closeConnection();
        }
        return utente;*/
    }

    public int checkUser(String username){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkUser.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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
                return 1;
        } catch (Throwable e) {
            Log.e("Error check username","Impossibile verificare l'username");
            return 2;
        }
        return 0;
        /*boolean isCon = connect();
        if(isCon==false)
            return 3;
        String query = "SELECT COUNT(*) AS count FROM utente WHERE username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");
                if(count!=0)
                    return 1;
            }
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error check username","Impossibile verificare l'username");
            return 2;
        }
        finally {
            closeConnection();
        }
        return 0;*/
    }

    public int checkEmail(String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", email));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/checkEmail.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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
                return 1;
        } catch (Throwable e) {
            Log.e("Error check email","Impossibile verificare l'email");
            return 2;
        }
        return 0;
        /*boolean isCon = connect();
        if(isCon==false)
            return 3;
        String query = "SELECT COUNT(*) AS count FROM utente WHERE email=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");
                if(count!=0)
                    return 1;
            }
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error check email","Impossibile verificare l'email");
            return 2;
        }
        finally {
            closeConnection();
        }
        return 0;*/
    }

    public ArrayList<String> prelevaUsernameAmici(String username){
        ArrayList<String> arrayList = new ArrayList<>();
        boolean isCon = connect();
        if(isCon==false)
            return null;
        String query = "SELECT username2 FROM amicizia WHERE username1=? ORDER BY username2 ASC";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String usernameAmico = rs.getString("username2");
                arrayList.add(usernameAmico);
            }
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        finally {
            closeConnection();
        }
        return arrayList;
    }

    public ArrayList<String> prelevaUsername(String username,String utenteCollegato){
        ArrayList<String> arrayList = new ArrayList<>();
        boolean isCon = connect();
        if(isCon==false)
            return null;
        String query = "SELECT username FROM utente WHERE username NOT IN (SELECT username FROM utente WHERE username=?) " +
                "AND username LIKE ? " +
                "AND username NOT IN (SELECT username1 FROM amicizia WHERE username2=?) " +
                "AND username NOT IN (SELECT username_destinatario FROM notifica WHERE username_mittente=? AND tipologia='RAR')";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, utenteCollegato);
            st.setString(2,username);
            st.setString(3,utenteCollegato);
            st.setString(4,utenteCollegato);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String usernameAmico = rs.getString("username");
                arrayList.add(usernameAmico);
            }
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        finally {
            closeConnection();
        }
        return arrayList;
    }

    public boolean aggiungiCoppiaAmici(String usernameMittente, String usernameDestinatario) {
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO amicizia (username1,username2) VALUES (?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, usernameMittente);
            st.setString(2, usernameDestinatario);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error agg amici","Impossibile aggiungere la coppia di utenti come amici");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
    }

    private void closeConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            Log.e("Error close connection","Impossibile chiudere la connessione");
            return;
        }
    }

}
