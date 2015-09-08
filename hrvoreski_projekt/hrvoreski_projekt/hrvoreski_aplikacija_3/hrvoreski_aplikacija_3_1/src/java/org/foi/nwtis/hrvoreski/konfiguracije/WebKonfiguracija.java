/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.konfiguracije;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.hrvoreski.konfiguracije.bp.BP_konfiguracija;

/**
 * Klasa koja se bavi učitavanjem i pružanjem konfiguracijskih podataka servera
 *
 * @author Hrvoje
 */
public class WebKonfiguracija {
    //adresa mail poslužitelja

    private BP_konfiguracija bpKonfiguracija;
    private String adresaPosluzitelja = "";
    private String poslanePoruke;
    private String trazeniPredmet;
    private String emailPort = "";
    private String korisnickoIme = "";
    private String lozinkaKorisnika = "";
    private String primateljEmailPoruke = "";
    private Konfiguracija config;
    private String datotekaAdrese;
    private String datotekaEmail;
    private int port;

    /**
     * Konstruktor konfiguracijske klase
     *
     * @param datoteka Lokacija datoteke konfiguracije
     */
    public WebKonfiguracija(String datoteka) {
        //ucitaj konfiguraciju baze podataka
        bpKonfiguracija = new BP_konfiguracija(datoteka);
        //ucitaj ostale parametre web servera
        try {
            config = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            this.adresaPosluzitelja = config.dajPostavku("adresaPosluzitelja");
            this.emailPort = config.dajPostavku("emailPort");
            this.korisnickoIme = config.dajPostavku("korisnickoIme");
            this.lozinkaKorisnika = config.dajPostavku("lozinka");
            this.trazeniPredmet = config.dajPostavku("trazeniPredmet");
            this.datotekaAdrese = config.dajPostavku("datotekaAdrese");
            this.datotekaEmail = config.dajPostavku("datotekaEmail");
            this.poslanePoruke = config.dajPostavku("poslanePoruke");
            this.port = Integer.valueOf(config.dajPostavku("port"));
            this.primateljEmailPoruke = config.dajPostavku("primateljEmailPoruke");

        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(WebKonfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

    public BP_konfiguracija getBpKonfiguracija() {
        return bpKonfiguracija;
    }

    public void setBpKonfiguracija(BP_konfiguracija bpKonfiguracija) {
        this.bpKonfiguracija = bpKonfiguracija;
    }

    public String getAdresaPosluzitelja() {
        return adresaPosluzitelja;
    }

    public void setAdresaPosluzitelja(String adresaPosluzitelja) {
        this.adresaPosluzitelja = adresaPosluzitelja;
    }

    public String getPoslanePoruke() {
        return poslanePoruke;
    }

    public void setPoslanePoruke(String poslanePoruke) {
        this.poslanePoruke = poslanePoruke;
    }

    public String getTrazeniPredmet() {
        return trazeniPredmet;
    }

    public void setTrazeniPredmet(String trazeniPredmet) {
        this.trazeniPredmet = trazeniPredmet;
    }

    public String getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinkaKorisnika() {
        return lozinkaKorisnika;
    }

    public void setLozinkaKorisnika(String lozinkaKorisnika) {
        this.lozinkaKorisnika = lozinkaKorisnika;
    }

    public String getPrimateljEmailPoruke() {
        return primateljEmailPoruke;
    }

    public void setPrimateljEmailPoruke(String primateljEmailPoruke) {
        this.primateljEmailPoruke = primateljEmailPoruke;
    }

    public Konfiguracija getConfig() {
        return config;
    }

    public void setConfig(Konfiguracija config) {
        this.config = config;
    }

    public String getDatotekaAdrese() {
        return datotekaAdrese;
    }

    public void setDatotekaAdrese(String datotekaAdrese) {
        this.datotekaAdrese = datotekaAdrese;
    }

    public String getDatotekaEmail() {
        return datotekaEmail;
    }

    public void setDatotekaEmail(String datotekaEmail) {
        this.datotekaEmail = datotekaEmail;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }





}
