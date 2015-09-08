/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.zrna;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.foi.nwtis.hrvoreski.web.kontrole.Korisnik;
import org.foi.nwtis.hrvoreski.web.rest.klijent.AktivniKorisniciKlijent;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * Bean za pregled aktivnih korisnika
 *
 * @author Hrvoje
 */
@Named(value = "pregledAktivnihKorisnika")
@Dependent
public class PregledAktivnihKorisnika {

    private Korisnik k;

    /**
     * Creates a new instance of PregledAktivnihKorisnika
     */
    public PregledAktivnihKorisnika() {
    }

    public Korisnik getK() {
        AktivniKorisniciKlijent akk = new AktivniKorisniciKlijent();
        String buffer = akk.getJson();
        if(buffer.isEmpty() || buffer == null){
            return null;
        }
        Korisnik k = new Korisnik();
        try {
            JSONObject jo = new JSONObject(buffer);
            k.setIme(jo.getString("ime"));
            k.setKorisnik(jo.getString("korIme"));
            k.setLozinka(jo.getString("lozinka"));
            k.setSes_ID(jo.getString("sesijaID"));
            k.setUloga(jo.getInt("uloga"));
            return k;

        } catch (JSONException ex) {
            Logger.getLogger(PregledAktivnihKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setK(Korisnik k) {
        this.k = k;
    }

}
