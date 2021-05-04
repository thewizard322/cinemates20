package com.example.cinemates20.Model;

public class Recensione {

    private int idRecenesione;
    private int idFilm;
    private String username;
    private String testo;
    private int valutazione;

    public Recensione(int idRecenesione, int idFilm, String username, String testo, int valutazione) {
        this.idRecenesione = idRecenesione;
        this.idFilm = idFilm;
        this.username = username;
        this.testo = testo;
        this.valutazione = valutazione;
    }

    public int getIdRecenesione() {
        return idRecenesione;
    }

    public int getIdFilm(){
        return idFilm;
    }

    public String getUsername() {
        return username;
    }

    public String getTesto() {
        return testo;
    }

    public int getValutazione() {
        return valutazione;
    }

}
