package com.example.cinemates20.Model;

import android.util.Patterns;

public class Utente {

    private static Utente utenteLoggato;
    private String username;
    private String password;
    private String email;

    public Utente(String username, String email){
        this.username = username;
        this.email = email;
    }

    public Utente(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int isValidate(){
        if(username.equals(""))
            return 1;
        if(username.length()<5)
            return 6;
        if(username.matches(".*\\s.*"))
            return 7;
        if(password.equals(""))
            return 2;
        if(password.length()<6)
            return 4;
        if(email.equals(""))
            return 5;
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return 3;
        return 0;
    }

    public static void setUtenteLoggato(Utente uL){
        if(utenteLoggato==null)
            utenteLoggato=uL;
    }

    public static void finalizeUtenteLoggato(){
        utenteLoggato = null;
    }

    public static Utente getUtenteLoggato(){
        return utenteLoggato;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
