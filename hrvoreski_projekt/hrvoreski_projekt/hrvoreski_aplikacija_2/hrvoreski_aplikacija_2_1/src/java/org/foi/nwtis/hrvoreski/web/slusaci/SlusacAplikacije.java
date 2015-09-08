/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.slusaci;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.ObradaPorukaDretva;



/**
 * Slušač životnog ciklusa aplikacije
 *
 * @author Hrvoje
 */
@WebListener()
public class SlusacAplikacije implements ServletContextListener {
    //dretva obrada poruke

    private ObradaPorukaDretva op;
    private Kontekst ctx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("WEB-INF");
        String datoteka = sce.getServletContext().getInitParameter("konfiguracija");
        WebKonfiguracija config = new WebKonfiguracija(path + File.separator + datoteka);
        sce.getServletContext().setAttribute("konfiguracija", config);
        System.out.println("Aplikacija je pokrenuta.");
        ctx = Kontekst.getInstance();
        ctx.setKontekst(sce.getServletContext());
        op = new ObradaPorukaDretva();
        op.setKonfig(config);
        op.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        op.setRadi(false);
        System.out.println("Aplikacija se zaustavlja.");
    }
}
