package com.example.cinemates20;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cinemates20.DAO.NotificaDAO;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CheckPreferitoGiaRaccomandatoBlackBoxTest {
    //TEST DAO. NEL DB È PRESENTE IL FILM RACCOMANDATO ["fly355","Giuliano1",41668]

    NotificaDAO notificaDAO = new NotificaDAO();

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE1_CE4_CE7() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato(null,null,-1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE1_CE5_CE7() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato(null,"",-1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE1_CE6_CE8() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato(null,"utente",1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE2_CE4_CE8() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato("",null,1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE2_CE5_CE8() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato("","",1));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE2_CE6_CE7() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato("","utente",-21));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE3_CE4_CE7() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato("utente",null,-2));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE3_CE5_CE7() {
        assertEquals(true,
                notificaDAO.checkPreferitoGiaRaccomandato("utente","",-2));
    }

    @Test
    public void checkPreferitoGiaRaccomandatoTestBlackBox_CE3_CE6_CE8() {
        assertEquals(false,notificaDAO.checkPreferitoGiaRaccomandato("fly355","Giuliano1", 41668));
    }

}
