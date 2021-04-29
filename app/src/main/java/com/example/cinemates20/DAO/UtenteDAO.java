package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UtenteDAO {

    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public int registerUser(String username, String pwd, String email){
        boolean isCon = connect();
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
        return 0;
    }

    public int login(String username, String password){
        boolean isCon = connect();
        if(isCon==false)
            return 3;
        String query = "SELECT COUNT(*) AS count FROM utente WHERE (username=? AND password=?)";
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

        return 0;
    }

    public Utente prelevaUtente(String username){
        boolean isCon = connect();
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
            Log.e("Error get user","Impossibile prelevare l'username");
            return null;
        }
        finally {
            closeConnection();
        }
        return utente;
    }

    public int checkUser(String username){
        boolean isCon = connect();
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
        return 0;
    }

    public int checkEmail(String email){
        boolean isCon = connect();
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
        return 0;
    }

    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            Log.e("Error close connection","Impossibile chiudere la connessione");
            return;
        }
    }

}
