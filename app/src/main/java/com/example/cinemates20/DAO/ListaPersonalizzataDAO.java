package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.ListaPersonalizzata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaPersonalizzataDAO {

    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public ArrayList<ListaPersonalizzata> prelevaListePersonalizzate(String username){
        ArrayList<ListaPersonalizzata> arrayList = new ArrayList<>();
        boolean isCon = connect();
        if(isCon==false)
            return arrayList;
        String query = "SELECT username, titolo, descrizione FROM lista_personalizzata WHERE username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                String user = rs.getString("username");
                String titolo = rs.getString("titolo");
                String descrizione = rs.getString("descrizione");
                ListaPersonalizzata listaPersonalizzata = new ListaPersonalizzata(titolo,descrizione,user);
                arrayList.add(listaPersonalizzata);
            }
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error prelievo","Impossibile prelevare le liste pers");
            return arrayList;
        }
        finally {
            closeConnection();
        }
        return arrayList;
    }

    public boolean aggiungiFilmAListaPers(String username, String titolo, int idFilm){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_lista_personalizzata (titolo,username,id_film) VALUES (?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, titolo);
            st.setString(2, username);
            st.setInt(3,idFilm);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add to pers","Impossibile aggiungere film alla lista pers");
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
