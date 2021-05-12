package com.example.cinemates20.Model;

public class Amicizia {
    private String usernameMittente;
    private String usernameDestinatario;

    public Amicizia(String usernameMittente, String usernameDestinatario){
        this.usernameMittente = usernameMittente;
        this.usernameDestinatario = usernameDestinatario;
    }

    public String getUsernameMittente() {
        return usernameMittente;
    }

    public String getUsernameDestinatario() {
        return usernameDestinatario;
    }
}