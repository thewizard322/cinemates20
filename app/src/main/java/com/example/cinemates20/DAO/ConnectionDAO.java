package com.example.cinemates20.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO {

    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.e("Error Class.ForName", "Impossibile trovare la classe");
            return null;
        }
        String url = "jdbc:mysql://cinematesdb.cuma8ibw5lcn.eu-central-1.rds.amazonaws.com/cinemates20" +
                "?autoReconnect=true&useSSL=false";
        String usr = "admin";
        String pwd = "cinematespwd";
        try {
            con = DriverManager.getConnection(url,usr,pwd);
        } catch (SQLException throwables) {
            Log.e("Error getConnection", "Impossibile connettersi");
            return null;
        }
        return con;
    }

}
