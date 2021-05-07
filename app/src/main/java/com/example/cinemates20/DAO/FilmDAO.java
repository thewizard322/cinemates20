package com.example.cinemates20.DAO;

import android.util.Log;

import com.example.cinemates20.Model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    private Connection con;

    public boolean connect(){
        con = ConnectionDAO.getConnection();
        if(con == null)
            return false;
        return true;
    }

    public boolean aggiungiAiPreferiti(String username, Film film){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_preferito (id_film,username,titolo,anno,posterpath) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, film.getId());
            st.setString(2, username);
            st.setString(3,film.getTitolo());
            st.setString(4,film.getDataUscita());
            st.setString(5,film.getPathPoster());
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

    public boolean aggiungiAiFilmDaVedere(String username, Film film){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "INSERT INTO film_da_vedere (id_film,username,titolo,anno,posterpath) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, film.getId());
            st.setString(2, username);
            st.setString(3,film.getTitolo());
            st.setString(4,film.getDataUscita());
            st.setString(5,film.getPathPoster());
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add film da veder","Impossibile aggiungere film da vedere");
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

    public ArrayList<Film> prelevaPreferiti(String username){
        ArrayList<Film> list = new ArrayList<Film>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film,titolo,anno,posterpath FROM film_preferito WHERE username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String titolo = rs.getString("titolo");
                String data = rs.getString("anno");
                String posterPath = rs.getString("posterpath");
                Film film = new Film(idF,titolo,data,posterPath);
                list.add(film);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo pref","Impossibile prelevare i preferiti");
            return list;
        }
        finally {
            closeConnection();
        }
        return list;
    }

    public ArrayList<Film> prelevaFilmDaVedere(String username){
        ArrayList<Film> list = new ArrayList<Film>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film,titolo,anno,posterpath FROM film_da_vedere WHERE username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String titolo = rs.getString("titolo");
                String data = rs.getString("anno");
                String posterPath = rs.getString("posterpath");
                Film film = new Film(idF,titolo,data,posterPath);
                list.add(film);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo FDV","Impossibile prelevare i film da vedere");
            return list;
        }
        finally {
            closeConnection();
        }
        return list;
    }

    public ArrayList<Integer> preleva_id_film_da_vedere(String username){
        ArrayList<Integer> list = new ArrayList<Integer>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film FROM film_da_vedere WHERE username=?";
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

    public boolean eliminaFilmDaVedere(String username, int idFilm){
        boolean isCon = connect();
        if(isCon==false)
            return false;
        String query = "DELETE FROM film_da_vedere WHERE id_film=? AND username=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, idFilm);
            st.setString(2, username);
            st.executeUpdate();
            st.close();
        } catch (SQLException throwables) {
            Log.e("Error add pref","Impossibile eliminare film da vedere");
            return false;
        }
        finally {
            closeConnection();
        }
        return true;
    }

    public ArrayList<Film> prelevafilmListaPersonalizzata(String username, String titoloLista){
        ArrayList<Film> list = new ArrayList<>();
        boolean isCon = connect();
        if(isCon==false)
            return list;
        String query = "SELECT id_film,titolo_lista,anno,posterpath FROM film_lista_personalizzata WHERE username=? AND titolo_lista=?";
        try {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, titoloLista);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int idF = rs.getInt("id_film");
                String titolo = rs.getString("titolo_lista");
                String data = rs.getString("anno");
                String posterPath = rs.getString("posterpath");
                Film film = new Film(idF,titolo,data,posterPath);
                list.add(film);
            }
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error prelievo listPers","Impossibile prelevare film dalla lista personalizzata");
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
