package com.example.cinemates20.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AmiciziaDAO {
    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
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

    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            Log.e("Error close connection","Impossibile chiudere la connessione");
            return;
        }
    }

}
