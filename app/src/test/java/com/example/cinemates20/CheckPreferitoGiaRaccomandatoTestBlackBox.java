package com.example.cinemates20;

import com.example.cinemates20.DAO.NotificaDAO;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckPreferitoGiaRaccomandatoTestBlackBox {

    NotificaDAO notificaDAO = new NotificaDAO();

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE1_CE4_CE7() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato(null,null,-1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE1_CE5_CE7() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato(null,"",-1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE1_CE6_CE8() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato(null,"utente",1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE2_CE4_CE8() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato("",null,1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE2_CE5_CE8() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato("","",1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE2_CE6_CE7() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato("","utente",-21));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE3_CE4_CE7() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato("utente",null,-2));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE3_CE5_CE7() {
        assertEquals(false,
                notificaDAO.checkPreferitoGiaRaccomandato("utente","",-2));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE3_CE6_CE8() {
        assertTrue(notificaDAO.checkPreferitoGiaRaccomandato("fly355","Giuliano1", 41668));
    }

}
