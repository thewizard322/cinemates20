package com.example.cinemates20;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cinemates20.Model.Utente;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

        Utente utente;

        @Test
        public void testIsValidateWhiteBox_1_2() {
            utente = new Utente("","password","email@email.com");
            assertEquals(1, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_4() {
            utente = new Utente("user","password","email@email.com");
            assertEquals(6, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_5_6() {
            utente = new Utente("user user","password","email@email.com");
            assertEquals(7, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_5_7_8() {
            utente = new Utente("user1","","email@email.com");
            assertEquals(2, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_5_7_9_10() {
            utente = new Utente("user1","pass","email@email.com");
            assertEquals(4, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_5_7_9_11_12() {
            utente = new Utente("user1","password","");
            assertEquals(5, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_5_7_9_11_13_14() {
            utente = new Utente("user1","password", "aaa");
            System.out.println(utente.isValidate());
            assertEquals(3, utente.isValidate());
        }

        @Test
        public void testIsValidateWhiteBox_1_3_5_7_9_11_13_15() {
            utente = new Utente("user1","password","aa@aa.com");
            assertEquals(0, utente.isValidate());
        }

}