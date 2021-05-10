package com.example.cinemates20.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificaDAO {

    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public boolean checkPreferitoGiaRaccomandato(String usernameMittente, String usernameDestinatario
                                    , int idFilm){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "SELECT COUNT(*) AS count FROM notifica WHERE username_mittente=? AND " +
                "username_destinatario=? AND id_film_preferito=? AND tipologia='RFP'";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, usernameMittente);
            st.setString(2, usernameDestinatario);
            st.setInt(3, idFilm);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");
                if(count!=0)
                    return false;
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error check prefracc","Impossibile Verificare preferito raccomandato");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
    }

    public boolean RaccomdandaPreferito(String usernameMittente, String usernameDestinatario
            , int idFilm, String titoloFilm){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO notifica (username_mittente,username_destinatario,id_film_preferito," +
                "titolo_film_preferito,tipologia) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, usernameMittente);
            st.setString(2, usernameDestinatario);
            st.setInt(3,idFilm);
            st.setString(4,titoloFilm);
            st.setString(5,"RFP");
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error racc pref","Impossibile raccomandare film preferito");
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
