package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Notifica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public boolean checkListaGiaRaccomandata(String usernameMittente, String usernameDestinatario
            , String titoloLista){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "SELECT COUNT(*) AS count FROM notifica WHERE username_mittente=? AND " +
                "username_destinatario=? AND titolo_lista=? AND tipologia='RLP'";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, usernameMittente);
            st.setString(2, usernameDestinatario);
            st.setString(3, titoloLista);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");
                if(count!=0)
                    return false;
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error check list","Impossibile Verificare lista raccomandata");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
    }

    public boolean RaccomdandaPreferito(String usernameMittente, String usernameDestinatario
            , int idFilm, String titoloFilm, String dataUscita, String posterPath){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO notifica (username_mittente,username_destinatario,id_film_preferito," +
                "titolo_film_preferito,data_film_preferito,posterpath_film_preferto,tipologia) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, usernameMittente);
            st.setString(2, usernameDestinatario);
            st.setInt(3,idFilm);
            st.setString(4,titoloFilm);
            st.setString(5,dataUscita);
            st.setString(6,posterPath);
            st.setString(7,"RFP");
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

    public boolean raccomandaLista(String usernameMittente, String usernameDestinatario
            , String titoloLista){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO notifica (username_mittente,username_destinatario,titolo_lista," +
                "username_lista,tipologia) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, usernameMittente);
            st.setString(2, usernameDestinatario);
            st.setString(3,titoloLista);
            st.setString(4,usernameMittente);
            st.setString(5,"RLP");
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error racc list","Impossibile raccomandare lista personalizzata");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
    }

    public ArrayList<Notifica> prelevaNotifiche(String username) {
        ArrayList<Notifica> list = new ArrayList<>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_notifica,username_mittente,username_destinatario,tipologia," +
                "id_film_preferito,titolo_film_preferito,titolo_lista,username_lista,like_dislike," +
                "commento FROM notifica WHERE username_destinatario=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idNotifica = rs.getInt("id_notifica");
                String usernameMittente = rs.getString("username_mittente");
                String usernameDestinatario = rs.getString("username_destinatario");
                String tipo = rs.getString("tipologia");
                String idFilmPreferito = rs.getString("id_film_preferito");
                String titoloFilmPreferito = rs.getString("titolo_film_preferito");
                String titoloLista = rs.getString("titolo_lista");
                String usernamelista = rs.getString("username_lista");
                int likeOrDislike = rs.getInt("like_dislike");
                String commento = rs.getString("commento");

                Notifica notifica = new Notifica(idNotifica,usernameMittente,usernameDestinatario,tipo);
                notifica.setIdFilmPreferito(idFilmPreferito);
                notifica.setTitoloFilmPreferito(titoloFilmPreferito);
                notifica.setTitoloLista(titoloLista);

                list.add(notifica);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo notif","Impossibile prelevare le notifiche");
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
