/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.zrna;

import org.foi.nwtis.hrvoreski.helperi.HelperZaBazu;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.hrvoreski.web.podaci.Location;

/**
 * Zrno za dodavanje nove adrese u bazu podataka
 * @author Hrvoje
 */
public class UnosAdrese {
    
    /**
     * Metoda koja dodaje novu adresu i njene geolokacijske info u bazu
     * @param adresa naziv adrese
     * @param config konfiguracije za spajanje
     * @param korIme ime korisnika
     */
    public void dodaj(String adresa, WebKonfiguracija config, String korIme) {
        GoogleMapsKlijent gmk = new GoogleMapsKlijent();
        Location loc = gmk.getGeoLocation(adresa);
        
        if (loc != null) {
            HelperZaBazu hzb = new HelperZaBazu(config);
            hzb.dodajAdresu(adresa, Double.valueOf(loc.getLatitude()), Double.valueOf(loc.getLongitude()), korIme);         
        }
    }
}
