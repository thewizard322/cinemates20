package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Recensione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {

    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public ArrayList<Recensione> prelevaRecensioniFilm(int idFilm){
        boolean isCon = connect();
        if(isCon==false)
            return null;
        String query = "SELECT id_film, username, testo, valutazione FROM recensione WHERE id_film = ?";
        ArrayList<Recensione> recensioneList = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String username = rs.getString("username");
                String testo = rs.getString("testo");
                int valutazione = rs.getInt("valutazione");
                Recensione recensione = new Recensione(idF,username,testo,valutazione);
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
        return recensioneList;
    }

    public boolean inserisciRecensione(String username, int idFilm, String testo, String voto){
        boolean isCon = connect();
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
