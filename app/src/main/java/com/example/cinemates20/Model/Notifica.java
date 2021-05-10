package com.example.cinemates20.Model;

//Utilizzare il costruttore per i campi obbligatori
//Utilizzare i Setter per i campi facoltativi

public class Notifica {

    //OBBLIGATORI
    private int idNotifica;
    private String usernameMittente;
    private String usernameDestinatario;
    private String tipo;

    //non vuoti se notifica raccomandazione film preferito
    private int idFilmPreferito;
    private String titoloFilmPreferito;

    //non vuoti se raccomandazione lista personalizzata o valutazione lista personalizzata
    private String titoloLista;

    //non vuoti se valutazione lista personalizzata (da associare a titoloLista)
    private int likeOrDislike; //0 = dislike, 1 = like
    private String commento; //OPZIONALE

    public Notifica(int idNotifica, String usernameMittente, String usernameDestinatario, String tipo){
        this.idNotifica = idNotifica;
        this.usernameMittente = usernameMittente;
        this.usernameDestinatario = usernameDestinatario;
        this.tipo = tipo;
    }

    public void setIdFilmPreferito(int idFilmPreferito) {
        this.idFilmPreferito = idFilmPreferito;
    }

    public void setTitoloFilmPreferito(String titoloFilmPreferito) {
        this.titoloFilmPreferito = titoloFilmPreferito;
    }

    public void setTitoloLista(String titoloLista) {
        this.titoloLista = titoloLista;
    }

    public void setLikeOrDislike(int likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }
}
