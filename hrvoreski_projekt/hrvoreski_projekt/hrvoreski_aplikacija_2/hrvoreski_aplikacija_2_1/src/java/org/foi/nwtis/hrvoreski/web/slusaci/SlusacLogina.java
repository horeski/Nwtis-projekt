/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.slusaci;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.foi.nwtis.hrvoreski.web.kontrole.Korisnik;

/**
 * Web application lifecycle listener. Sluša HTTP sesiju i registrira svakog
 * prijavljenog korisnika i njegove informacije za REST servis
 *
 * @author Hrvoje
 */
@WebListener()
public class SlusacLogina implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().compareTo("korisnik") != 0) {
            return;
        }
        Korisnik korisnik = (Korisnik) event.getSession().getAttribute("korisnik");
        List<Korisnik> popisKorisnika = (List<Korisnik>) event.getSession().getServletContext().getAttribute("aktivniKorisnici");
        if (popisKorisnika == null) {
            popisKorisnika = new ArrayList();
        }
        popisKorisnika.add(korisnik);
        event.getSession().getServletContext().setAttribute("aktivniKorisnici", popisKorisnika);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().compareTo("korisnik") != 0) {
            return;
        }
        Korisnik korisnik = (Korisnik) event.getSession().getAttribute("korisnik");
        List<Korisnik> popisKorisnika = (List<Korisnik>) event.getSession().getServletContext().getAttribute("aktivniKorisnici");
        if (popisKorisnika == null) {
            popisKorisnika = new ArrayList();
        }
        popisKorisnika.remove(korisnik);
        event.getSession().getServletContext().setAttribute("aktivniKorisnici", popisKorisnika);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
