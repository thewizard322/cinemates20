package com.example.cinemates20.Model;

public class ListaPersonalizzata {

    private String titolo;
    private String descrizione;
    private String username;

    public ListaPersonalizzata(String titolo, String descrizione, String username) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.username = username;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getUsername() {
        return username;
    }
}
