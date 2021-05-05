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
            Log.e("Error prelievo","Impossibile prelevare gli id");
            return arrayList;
        }
        finally {
            closeConnection();
        }
        return arrayList;
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
