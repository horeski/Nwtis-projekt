/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.app;

import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;

/**
 * Pokretanje preuzimanja meteo podataka po zadanom intervalu
 * @author Hrvoje
 */
public class MeteoPreuzimanje {
    
    private WebKonfiguracija config;
    private static boolean pauza = false;
    private static boolean kraj = false;
    private DretvaMeteoPreuzimanje dmp;
    
    /**
     * Konstruktor za inacijalizaciju konfiguracije
     * @param config konfiguracijska datoteka
     */
    MeteoPreuzimanje(WebKonfiguracija config) {
        this.config = config;
    }
    
    /**
     * Pokreće preuzimanja meteo podataka otvaranjem socketa i čekanjem na
     * klijente
     */
    public void pokreniPreuzimanje(){
        dmp = new DretvaMeteoPreuzimanje(config);
        dmp.start();
    }
    /**
     * Prekida rad dretve
     */
    public void zaustaviPreuzimanje(){
        dmp.interrupt();
    }
    
    /**
     * Dali je server pauziran
     * @return 
     */
    public static boolean isPauza() {
        return pauza;
    }
    
    /**
     * Postavi server u pauzu
     * @param pauza 
     */
    public static void setPauza(boolean pauza) {
        MeteoPreuzimanje.pauza = pauza;
    }
    
    /**
     * Da li je server završio s radom
     * @return 
     */
    public static boolean isKraj() {
        return kraj;
    }
    
    /**
     * Postavi vrijednost za kraj rada servera
     * @param kraj 
     */
    public static void setKraj(boolean kraj) {
        MeteoPreuzimanje.kraj = kraj;
    }
    
    
}
