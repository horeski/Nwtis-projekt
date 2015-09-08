/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.helperi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;

/**
 * Dnevnik za aplikaciju, hrvoreski_aplikacija_1
 * @author Hrvoje
 */
public class Dnevnik {
    private WebKonfiguracija config;
    private String korIme;
    private String korLozinka;
    private String host;
    private String nazivBaze;
    private String driver;
    private Statement naredba;
    private Connection veza;
    
    /**
     * Konstruktor, inacijalizacija varijabli
     * @param config konfiguracijska datoteka
     */
    public Dnevnik(WebKonfiguracija config) {
        this.config = config;
        korIme = config.getBpKonfiguracija().getUser_username();
        korLozinka = config.getBpKonfiguracija().getUser_password();
        nazivBaze = config.getBpKonfiguracija().getUser_database();
        host = config.getBpKonfiguracija().getServer_database();
        this.driver = config.getBpKonfiguracija().getDriver_database();
    }
    /**
     * Zapisuje sve dobivene podatke u dnevnik
     * @param vrijeme  vrijeme primljene naredbe
     * @param korisnik korisnik koji je poslao naredbu
     * @param komanda komanda koja je primljena
     * @param odgovor odgovor na traženu komandu od istog korisnika
     */
    public void zapisiDnevnik (String vrijeme, String korisnik, String komanda, String odgovor ){
        String upit = "INSERT INTO hrvoreski_dnevnik(vrijeme, korisnik, komanda, odgovor) "
                + "VALUES('" + vrijeme + "','" + korisnik + "','" + komanda + "','" + odgovor + "')";
        try {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException ex) {
                ex.fillInStackTrace();
                System.out.println("Greška kod učitavanja / driver baze ne postoji !");
            }
            veza = DriverManager.getConnection(host + nazivBaze, korIme, korLozinka);
            naredba = veza.createStatement();
            naredba.executeUpdate(upit);
            veza.close();
        } catch (SQLException ex) {
            System.out.println("Greška pri komunikaciji s bazom: DNEVNIK " + ex.toString());
        }
        
    }
    
    
}
