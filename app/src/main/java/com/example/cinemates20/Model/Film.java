package com.example.cinemates20.Model;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable {

    private int id;
    private String titolo;
    private String dataUscita;
    private List<String> generi;
    private String trama;
    private String regista;
    private List<String> attori;
    private String pathPoster;

    public Film(int id, String titolo, String dataUscita, String pathPoster){
        this.id = id;
        this.titolo = titolo;
        this.dataUscita = dataUscita;
        this.pathPoster = pathPoster;
    }

    public Film(String titolo, String dataUscita, String trama, List<String> generi, String regista, List<String> attori,
                String pathPoster){
        this.titolo = titolo;
        this.dataUscita = dataUscita;
        this.generi = generi;
        this.trama = trama;
        this.regista = regista;
        this.attori = attori;
        this.pathPoster = pathPoster;
    }

    public int getId(){
        return id;
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

    public List<String> getGenere(){
        return generi;
    }

    public String getPatPosterW92() {
        return "https://image.tmdb.org/t/p/w92"+pathPoster;
    }

    public String getPatPosterW154(){
        return "https://image.tmdb.org/t/p/w154"+pathPoster;
    }

    public String getTrama() {
        return trama;
    }

    public String getDataUscita(){
        return dataUscita;
    }

    public String getStringAttori(){
        String ret = "";
        for(String attore : attori){
            ret = ret + attore + ", ";
        }
        if(ret.endsWith(", ")){
            ret = ret.substring(0,ret.length()-2);
        }
        return ret;
    }

    public String getStringGeneri(){
        String ret = "";
        for(String genere : generi){
            ret = ret + genere + ", ";
        }
        if(ret.endsWith(", ")){
            ret = ret.substring(0,ret.length()-2);
        }
        return ret;
    }

    public void setGeneri(List<String> generi){
        this.generi = generi;
    }

    public void setTrama(String trama){
        this.trama = trama;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setAttori(List<String> attori) {
        this.attori = attori;
    }
}
