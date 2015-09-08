/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.kontrole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ova klasa priprema podatke za serijalizaciju, znači adrese i mailove
 * @author Hrvoje
 */
public class SpremnikKlasa implements Serializable{
    
    private List<JMSAdresaStruktura> adresaPoruke = new ArrayList<>();
    private List<JMSPorukaStruktura> emailPoruke = new ArrayList<>();
    private int counter = 0;

    public SpremnikKlasa() {
    }

    public List<JMSAdresaStruktura> getAdresaPoruke() {
        return adresaPoruke;
    }

    public void setAdresaPoruke(List<JMSAdresaStruktura> adresaPoruke) {
        this.adresaPoruke = adresaPoruke;
    }

    public List<JMSPorukaStruktura> getEmailPoruke() {
        return emailPoruke;
    }

    public void setEmailPoruke(List<JMSPorukaStruktura> emailPoruke) {
        this.emailPoruke = emailPoruke;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    
    
}
