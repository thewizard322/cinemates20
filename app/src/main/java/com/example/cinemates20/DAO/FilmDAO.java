package com.example.cinemates20.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//mannacc a maronn
public class FilmDAO {

    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public boolean aggiungiAiPreferiti(String username, int idFilm){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_preferito (id_film,username) VALUES (?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            st.setString(2, username);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add pref","Impossibile aggiungere film preferito");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
    }

    public boolean aggiungiAiFilmDaVedere(String username, int idFilm){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_da_vedere (id_film,username) VALUES (?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            st.setString(2, username);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add pref","Impossibile aggiungere film da vedere");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
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
