/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.zrna;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.foi.nwtis.hrvoreski.sb.MessagesStorage;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSAdresaStruktura;

/**
 * Pregled JMS adresa poruka managed bean
 *
 * @author Hrvoje
 */
@Named(value = "pregledAdresaPoruka")
@Dependent
public class PregledAdresaPoruka {

    @EJB
    private MessagesStorage messagesStorage;
    private List<JMSAdresaStruktura> listaAdresa;

    public PregledAdresaPoruka() {
    }

    @PostConstruct
    private void init() {
        listaAdresa = messagesStorage.getAdresaPoruke();
    }

    public List<JMSAdresaStruktura> getListaAdresa() {
        return listaAdresa;
    }

    public void setListaAdresa(List<JMSAdresaStruktura> listaAdresa) {
        this.listaAdresa = listaAdresa;
    }

    public String brisiPoruku(JMSAdresaStruktura poruka) {
        listaAdresa = messagesStorage.getAdresaPoruke();
        listaAdresa.remove(poruka);
        messagesStorage.setAdresaPoruke(listaAdresa);
        return null;
    }

    public String brisiSvePoruke() {
        listaAdresa = messagesStorage.getAdresaPoruke();
        listaAdresa.clear();
        messagesStorage.setAdresaPoruke(listaAdresa);
        return null;
    }

}
