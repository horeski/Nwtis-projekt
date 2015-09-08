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
import org.foi.nwtis.hrvoreski.web.kontrole.JMSPorukaStruktura;

/**
 * Pregled JMS mail poruka managed bean
 *
 * @author Hrvoje
 */
@Named(value = "pregledMailPoruka")
@Dependent
public class PregledMailPoruka {

    @EJB
    private MessagesStorage messagesStorage;
    private List<JMSPorukaStruktura> listaPoruka = new ArrayList<>();

    public PregledMailPoruka() {
    }

    @PostConstruct
    private void init() {
        listaPoruka = messagesStorage.getEmailPoruke();
    }

    public List<JMSPorukaStruktura> getListaPoruka() {
        return listaPoruka;
    }

    public void setListaPoruka(List<JMSPorukaStruktura> listaPoruka) {
        this.listaPoruka = listaPoruka;
    }

    public String brisiPoruku(JMSPorukaStruktura poruka) {
        listaPoruka = messagesStorage.getEmailPoruke();
        listaPoruka.remove(poruka);
        messagesStorage.setEmailPoruke(listaPoruka);
        return null;
    }

    public String brisiSvePoruke() {
        listaPoruka = messagesStorage.getEmailPoruke();
        listaPoruka.clear();
        messagesStorage.setEmailPoruke(listaPoruka);
        return null;
    }

}
