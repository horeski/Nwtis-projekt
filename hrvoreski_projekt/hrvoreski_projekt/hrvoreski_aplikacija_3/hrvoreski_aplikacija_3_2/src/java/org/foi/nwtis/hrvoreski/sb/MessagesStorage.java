/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSAdresaStruktura;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSPorukaStruktura;

/**
 *
 * @author Hrvoje
 */
@Singleton
@LocalBean
public class MessagesStorage {

    private List<JMSAdresaStruktura> adresaPoruke = new ArrayList<>();
    private List<JMSPorukaStruktura> emailPoruke = new ArrayList<>();
    private int counter = 0;

    public MessagesStorage() {
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

    public void incrementCounter() {
        counter++;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void dodajPoruku(JMSPorukaStruktura poruka) {
        emailPoruke.add(poruka);
    }

    public void dodajAdresu(JMSAdresaStruktura poruka) {
        adresaPoruke.add(poruka);
    }

    public void brisiAdresu(JMSAdresaStruktura adresa) {
        this.adresaPoruke.remove(adresa);
    }

    public void brisiPoruku(JMSPorukaStruktura adresa) {
        this.emailPoruke.remove(adresa);
    }
}
