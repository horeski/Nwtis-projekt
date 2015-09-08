/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.konfiguracije;

import org.foi.nwtis.hrvoreski.konfiguracije.bp.BP_konfiguracija;

/**
 *
 * @author Hrvoje
 */
public class WebKonfiguracija {
        //adresa mail poslužitelja

    private BP_konfiguracija bpKonfiguracija;
    //interval preuzimanja meteo podataka
    private int interval;
    //port socket servera
    private int port;
    private Konfiguracija config;
    private String cKey;
    private String sKey;
    //adresa email posluzitelja
    private String adresaPosluzitelja = "";
    //port posluzitelja
    private String emailPort = "";
    //adresa (username) provjeravanoga maila
    private String korisnickoIme = "";
    //lozinka provjeravanoga maila
    private String lozinkaKorisnika = "";
    //kljucna rijec subjecta  
    private String trazeniPredmet;
    //direktorij poslanih poruka
    private String dirPoslanePoruke;
    //primatelj email poruke
    private String primateljEmailPoruke;

    /**
     * Konstruktor konfiguracijske klase
     *
     * @param datoteka Lokacija datoteke konfiguracije
     */
    public WebKonfiguracija(String datoteka) throws NemaKonfiguracije {
        //ucitaj konfiguraciju baze podataka
        bpKonfiguracija = new BP_konfiguracija(datoteka);
        //ucitaj ostale parametre web servera

        config = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
        this.interval = Integer.valueOf(config.dajPostavku("interval"));
        this.port = Integer.valueOf(config.dajPostavku("port"));
        this.cKey = config.dajPostavku("cKey");
        this.sKey = config.dajPostavku("sKey"); 
        this.adresaPosluzitelja = config.dajPostavku("adresaPosluzitelja");
        this.emailPort = config.dajPostavku("emailPort");
        this.korisnickoIme = config.dajPostavku("korisnickoIme");
        this.lozinkaKorisnika = config.dajPostavku("lozinka");
        this.dirPoslanePoruke = config.dajPostavku("poslanePoruke");
        this.trazeniPredmet = config.dajPostavku("trazeniPredmet");
        this.primateljEmailPoruke=config.dajPostavku("primateljEmailPoruke");
    }

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }

    public String getsKey() {
        return sKey;
    }

    public void setsKey(String sKey) {
        this.sKey = sKey;
    }


    public Konfiguracija getConfig() {
        return config;
    }

    public BP_konfiguracija getBpKonfiguracija() {
        return bpKonfiguracija;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAdresaPosluzitelja() {
        return adresaPosluzitelja;
    }

    public void setAdresaPosluzitelja(String adresaPosluzitelja) {
        this.adresaPosluzitelja = adresaPosluzitelja;
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

    public String getDirPoslanePoruke() {
        return dirPoslanePoruke;
    }

    public void setDirPoslanePoruke(String dirPoslanePoruke) {
        this.dirPoslanePoruke = dirPoslanePoruke;
    }

    public String getPrimateljEmailPoruke() {
        return primateljEmailPoruke;
    }

    public void setPrimateljEmailPoruke(String primateljEmailPoruke) {
        this.primateljEmailPoruke = primateljEmailPoruke;
    }

    public String getTrazeniPredmet() {
        return trazeniPredmet;
    }

    public void setTrazeniPredmet(String trazeniPredmet) {
        this.trazeniPredmet = trazeniPredmet;
    }
    
}
