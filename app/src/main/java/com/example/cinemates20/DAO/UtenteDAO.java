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

import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    private String url = "http://ec2-18-196-42-56.eu-central-1.compute.amazonaws.com";

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
    }

    public ArrayList<String> prelevaUsernameAmici(String username){
        ArrayList<String> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaUsernameAmici.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String usernameAmico = jsonObject.getString("amico");
                arrayList.add(usernameAmico);
            }
        } catch (Throwable e) {
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        return arrayList;
    }

    public ArrayList<String> prelevaUsernameRicerca(String usernameCercato,String usernameCollegato){
        ArrayList<String> arrayList = new ArrayList<>();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username_collegato", usernameCollegato));
        params.add(new BasicNameValuePair("username_cercato", usernameCercato));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/prelevaUsernameRicerca.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONArray jsonArray = new JSONArray(responseString);
            int n = jsonArray.length();
            for (int i = 0; i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String usernameTrovato = jsonObject.getString("username");
                arrayList.add(usernameTrovato);
            }
        } catch (Throwable e) {
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        return arrayList;
    }

    public boolean aggiungiCoppiaAmici(String username1, String username2) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username1", username1));
        params.add(new BasicNameValuePair("username2", username2));
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url+"/aggiungiCoppiaAmici.php");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
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

}
