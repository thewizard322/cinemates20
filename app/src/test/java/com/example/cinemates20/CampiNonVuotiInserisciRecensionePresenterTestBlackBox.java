package com.example.cinemates20;

import org.junit.Test;

import static org.junit.Assert.*;

public class CampiNonVuotiInserisciRecensionePresenterTestBlackBox {

    public boolean campiNonVuoti(String testo, String votoRecensione){
        if(testo.equals("") || votoRecensione.equals(""))
            return false;
        return true;
    }

    @Test (expected = NullPointerException.class)
    public void testCampiNonVuotiBlackBox_CE1_CE4(){
        campiNonVuoti(null,null);
    }

    @Test (expected = NullPointerException.class)
    public void testCampiNonVuotiBlackBox_CE1_CE5(){
        campiNonVuoti(null,"");
    }

    @Test (expected = NullPointerException.class)
    public void testCampiNonVuotiBlackBox_CE1_CE6(){
        campiNonVuoti(null,"1");
    }

    @Test
    public void testCampiNonVuotiBlackBox_CE2_CE4(){
        assertEquals(false,campiNonVuoti("",null));
    }

    @Test
    public void testCampiNonVuotiBlackBox_CE2_CE5(){
        assertEquals(false,campiNonVuoti("",""));
    }

    @Test
    public void testCampiNonVuotiBlackBox_CE2_CE6(){
        assertEquals(false,campiNonVuoti("","1"));
    }

    @Test (expected = NullPointerException.class)
    public void testCampiNonVuotiBlackBox_CE3_CE4(){
         campiNonVuoti("testo",null);
    }

    @Test
    public void testCampiNonVuotiBlackBox_CE3_CE5(){
        assertEquals(false,campiNonVuoti("testo",""));
    }

    @Test
    public void testCampiNonVuotiBlackBox_CE3_CE6(){
        assertEquals(true,campiNonVuoti("testo","1"));
    }

}
