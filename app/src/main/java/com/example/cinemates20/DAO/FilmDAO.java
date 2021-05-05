package com.example.cinemates20.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public boolean eliminaDaPreferiti(String username, int idFilm){
        boolean isCon = connect();
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
        return true;
    }
    public ArrayList<Integer> preleva_id_preferiti(String username){
        ArrayList<Integer> list = new ArrayList<Integer>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film FROM film_preferito WHERE username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                list.add(idF);
            }
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error prelievo","Impossibile prelevare gli id");
            return list;
        }
        finally {
            closeConnection();
        }
        return list;
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
