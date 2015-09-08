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
    //port posluzitelja
    private String emailPort = "";
    //interval provjere maila
    private int interval;
    //adresa (username) provjeravanoga maila
    private String korisnickoIme = "";
    //lozinka provjeravanoga maila
    private String lozinkaKorisnika = "";
    //kljucna rijec subjecta  
    private String trazeniPredmet;
    //direktorij galerije web aplikacije
    private String dirGalerijeAplikacije;
    //direktorij neispravnih poruka
    private String dirNeispravnePoruke;
    //direktorij ostalih poruka
    private String dirOstalePoruke;
    //direktorij ispravnih poruka
    private String dirIspravnePoruke;
    //direktorij statistike
    private String dirStatistike;
    //adresa maila za slanje statistike
    private String statistikaMailAdresa;
    //predmet (subject) maila statistike poruka
    private String predmetMailaStatistike;
    //direktorij poslanih poruka
    private String dirPoslanePoruke;
    private int brojUcitanihPoruka;
    private Konfiguracija config;
    private String cKey;
    private String sKey;
    private int portfelji;

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
            this.cKey = config.dajPostavku("cKey");
            this.sKey = config.dajPostavku("sKey");
            this.adresaPosluzitelja = config.dajPostavku("adresaPosluzitelja");
            this.dirIspravnePoruke = config.dajPostavku("dirIspravnePoruke");
            this.dirNeispravnePoruke = config.dajPostavku("dirNeispravnePoruke");
            this.dirOstalePoruke = config.dajPostavku("dirOstalePoruke");
            this.dirGalerijeAplikacije = config.dajPostavku("dirGalerijeAplikacije");
            this.emailPort = config.dajPostavku("emailPort");
            this.korisnickoIme = config.dajPostavku("korisnickoIme");
            this.lozinkaKorisnika = config.dajPostavku("lozinka");
            this.dirPoslanePoruke = config.dajPostavku("poslanePoruke");
            this.predmetMailaStatistike = config.dajPostavku("predmetMailaStatistike");
            this.statistikaMailAdresa = config.dajPostavku("statistikaMail");
            this.trazeniPredmet = config.dajPostavku("trazeniPredmet");
            this.interval = Integer.valueOf(config.dajPostavku("interval"));
            this.dirStatistike = config.dajPostavku("dirStatistike");
            this.brojUcitanihPoruka = Integer.valueOf(config.dajPostavku("brojUcitanihPoruka"));
            this.portfelji = Integer.valueOf(config.dajPostavku("portfelji"));

        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(WebKonfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getPortfelji() {
        return portfelji;
    }

    public void setPortfelji(int portfelji) {
        this.portfelji = portfelji;
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

    public int getBrojUcitanihPoruka() {
        return brojUcitanihPoruka;
    }

    public void setBrojUcitanihPoruka(int brojUcitanihPoruka) {
        this.brojUcitanihPoruka = brojUcitanihPoruka;
    }

    public Konfiguracija getConfig() {
        return config;
    }

    public BP_konfiguracija getBpKonfiguracija() {
        return bpKonfiguracija;
    }

    public String getDirStatistike() {
        return dirStatistike;
    }

    public void setDirStatistike(String dirStatistike) {
        this.dirStatistike = dirStatistike;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
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

    public String getTrazeniPredmet() {
        return trazeniPredmet;
    }

    public void setTrazeniPredmet(String trazeniPredmet) {
        this.trazeniPredmet = trazeniPredmet;
    }

    public String getDirGalerijeAplikacije() {
        return dirGalerijeAplikacije;
    }

    public void setDirGalerijeAplikacije(String dirGalerijeAplikacije) {
        this.dirGalerijeAplikacije = dirGalerijeAplikacije;
    }

    public String getDirNeispravnePoruke() {
        return dirNeispravnePoruke;
    }

    public void setDirNeispravnePoruke(String dirNeispravnePoruke) {
        this.dirNeispravnePoruke = dirNeispravnePoruke;
    }

    public String getDirOstalePoruke() {
        return dirOstalePoruke;
    }

    public void setDirOstalePoruke(String dirOstalePoruke) {
        this.dirOstalePoruke = dirOstalePoruke;
    }

    public String getDirIspravnePoruke() {
        return dirIspravnePoruke;
    }

    public void setDirIspravnePoruke(String dirIspravnePoruke) {
        this.dirIspravnePoruke = dirIspravnePoruke;
    }

    public String getStatistikaMailAdresa() {
        return statistikaMailAdresa;
    }

    public void setStatistikaMailAdresa(String statistikaMailAdresa) {
        this.statistikaMailAdresa = statistikaMailAdresa;
    }

    public String getPredmetMailaStatistike() {
        return predmetMailaStatistike;
    }

    public void setPredmetMailaStatistike(String predmetMailaStatistike) {
        this.predmetMailaStatistike = predmetMailaStatistike;
    }

    public String getDirPoslanePoruke() {
        return dirPoslanePoruke;
    }

    public void setDirPoslanePoruke(String dirPoslanePoruke) {
        this.dirPoslanePoruke = dirPoslanePoruke;
    }
}
