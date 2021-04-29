package com.example.cinemates20.Model;

import java.util.List;

public class Film {

    private String titolo;
    private String dataUscita;
    private String genere;
    private String trama;
    private String regista;
    private List<String> attori;
    private String pathPoster;

    public Film(String titolo, String dataUscita, String pathPoster){
        this.titolo = titolo;
        this.dataUscita = dataUscita;
        this.pathPoster = pathPoster;
    }

    public Film(String titolo, String dataUscita, String trama, String genere, String regista, List<String> attori,
                String pathPoster){
        this.titolo = titolo;
        this.dataUscita = dataUscita;
        this.genere = genere;
        this.trama = trama;
        this.regista = regista;
        this.attori = attori;
        this.pathPoster = pathPoster;
    }

    public String getTitolo(){
        return titolo;
    }

    public String getRegista() {
        return regista;
    }

    public List<String> getAttori() {
        return attori;
    }

    public String getGenere(){
        return genere;
    }

    public String getPatPosterW92() {
        return "https://image.tmdb.org/t/p/w92"+pathPoster;
    }

    public String getTrama() {
        return trama;
    }

    public String getDataUscita(){
        return dataUscita;
    }

}
