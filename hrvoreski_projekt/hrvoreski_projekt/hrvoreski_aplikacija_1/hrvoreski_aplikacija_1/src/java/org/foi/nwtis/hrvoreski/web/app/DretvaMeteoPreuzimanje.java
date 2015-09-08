/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.rest.klijenti.WeatherBugKlijent;
import org.foi.nwtis.hrvoreski.web.podaci.WeatherData;

/**
 * Dretva koja preuzima meteo podatke
 *
 * @author Hrvoje
 */
public class DretvaMeteoPreuzimanje extends Thread {

    private WebKonfiguracija config = null;
    private int interval;
    private String driver;
    private boolean radi;
    private Date startObrade;
    private Date krajObrade;
    private WeatherBugKlijent wbk;
    private WeatherData wd;
    private String korisnickoIme;
    private String korisnickaLozinka;
    private String nazivBaze;
    private String host;
    private Connection veza;
    private Statement instrukcija;
    private Statement instrukcija2;
    private ResultSet odgovor;
    private String url;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");

    /**
     *
     * @param config konfiguracija servera
     */
    DretvaMeteoPreuzimanje(WebKonfiguracija config) {
        radi = true;
        this.setName("DretvaMeteoPreuzimanje");
        this.config = config;
        this.interval = config.getInterval();
        this.driver = config.getBpKonfiguracija().getDriver_database();
        korisnickoIme = config.getBpKonfiguracija().getUser_username();
        korisnickaLozinka = config.getBpKonfiguracija().getUser_password();
        nazivBaze = config.getBpKonfiguracija().getUser_database();
        host = config.getBpKonfiguracija().getServer_database();
        driver = config.getBpKonfiguracija().getDriver_database();
        wbk = new WeatherBugKlijent(config.getcKey(), config.getsKey());
        url = host + nazivBaze;
    }

    /**
     * Metoda pokretanja dretve servera vremena
     */
    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Obrada klijenta");
        radi = true;
    }

    /**
     * Metoda izvršavanja servera vremena, te spavanje dretve temeljem intervala
     */
    @Override
    public void run() {
        try {
            while (radi) {
                if (MeteoPreuzimanje.isKraj() != true) {
                    startObrade = new Date();
                    preuzmiPodatke();
                    long trajanje = krajObrade.getTime() - startObrade.getTime();
                    System.out.println("Spavanje u vrijednosti od :" + String.valueOf(trajanje));
                    sleep(interval * 1000 - trajanje);
                    
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DretvaMeteoPreuzimanje.class.getName()).log(Level.SEVERE, null, "Prekidam preuzimanje podataka :" + ex);
        }
    }
    /**
     * Preuzima adrese koje su označene s statusom da se za njih dohvaćaju meteo podaci
     */
    public void preuzmiPodatke() {
        String upit = "SELECT * FROM hrvoreski_adrese";
        try {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                ex.fillInStackTrace();
                System.out.println("Driver ne postoji ili greška kod učitavanja !");
            }
            
            veza = DriverManager.getConnection(url, korisnickoIme, korisnickaLozinka);
            instrukcija = veza.createStatement();
            if (!MeteoPreuzimanje.isPauza()) {
                odgovor = instrukcija.executeQuery(upit);
                while (odgovor.next()) {            
                    String adresa = odgovor.getString("adresa");
                    int idAdresa = Integer.parseInt(odgovor.getString("idAdresa")); 
                    String latitude = odgovor.getString("latitude");
                    String longitude = odgovor.getString("longitude");
                    wd = wbk.getRealTimeWeather(latitude, longitude);
                    if (wd.getTemperature() == null || wd.getWindSpeed() == null) {
                        //ukoliko nije vratio nikakve podatke za adresu, da se ne sprema glupost u bazu pa prekini rad
                        break;
                    } else {
                        System.out.println("Adresa: " + adresa + ", Temperatura: " + wd.getTemperature());
                        Float temp = wd.getTemperature();
                        Float tlak = wd.getPressureSeaLevel();
                        Float vlaga = wd.getHumidity();
                        Float vjetar = wd.getWindSpeed();
                        Float kisa = wd.getRainRate();
                        Float snijeg = wd.getSnowRate();
                        String vrijeme = sdf.format(new Date());
                        String upit2 = "INSERT INTO hrvoreski_meteo (temperatura, tlak, vlaga, vjetar, kisa, snijeg, vrijeme, idAdresa)"
                                + "VALUES (" + temp + ", " + tlak + ", " + vlaga + ", "
                                + vjetar + ", " + kisa + ", " + snijeg + ", '" + vrijeme + "', " + idAdresa + ")";
                        instrukcija2 = veza.createStatement();
                        instrukcija2.executeUpdate(upit2);
                    }
                }
            }
            veza.close();
        } catch (SQLException ex) {
            System.out.println("Greška pri komunikaciji s bazom: " + ex.toString());
        }
        krajObrade = new Date();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        radi = false;

    }
}
