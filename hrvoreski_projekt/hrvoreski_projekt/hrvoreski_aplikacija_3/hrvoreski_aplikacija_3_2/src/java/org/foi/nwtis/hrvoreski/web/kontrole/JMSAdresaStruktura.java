/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.kontrole;

import java.io.Serializable;

/**
 *  Struktura JMS za adrese
 * @author Hrvoje
 */
public class JMSAdresaStruktura implements Serializable{
    
    
    private String adresa;
    private int brisi;
    /**
     * konstruktor
     * @param adresa naziv adrese
     */
    public JMSAdresaStruktura(String adresa) {
        this.adresa = adresa;
        brisi = 0;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getBrisi() {
        return brisi;
    }

    public void setBrisi(int brisi) {
        this.brisi = brisi;
    }  
   
}
