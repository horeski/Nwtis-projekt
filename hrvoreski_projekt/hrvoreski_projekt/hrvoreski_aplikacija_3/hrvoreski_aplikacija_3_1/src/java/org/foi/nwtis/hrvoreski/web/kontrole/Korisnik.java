/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.kontrole;

/**
 *
 * @author Hrvoje
 */
public class Korisnik {

    String korisnik;
    String ime;
    String lozinka;
    String ses_ID;
    int uloga;

    public Korisnik(String korisnik, String ime, String lozinka, String ses_ID, int uloga) {
        this.korisnik = korisnik;
        this.ime = ime;
        this.lozinka = lozinka;
        this.ses_ID = ses_ID;
        this.uloga = uloga;
    }

    public Korisnik() {
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getSes_ID() {
        return ses_ID;
    }

    public void setSes_ID(String ses_ID) {
        this.ses_ID = ses_ID;
    }

    public int getUloga() {
        return uloga;
    }

    public void setUloga(int uloga) {
        this.uloga = uloga;
    }





}
