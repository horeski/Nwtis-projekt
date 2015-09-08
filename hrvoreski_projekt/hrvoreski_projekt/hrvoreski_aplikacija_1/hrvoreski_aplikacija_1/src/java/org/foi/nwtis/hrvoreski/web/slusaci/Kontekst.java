/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.slusaci;

import javax.servlet.ServletContext;

/**
 * Singleton uz pomoć kojeg možemo pristupiti kontekstu applikacije
 * @author Hrvoje
 */
public class Kontekst {

    private static final Kontekst INSTANCE = new Kontekst();

    // Private constructor prevents instantiation from other classes
    private Kontekst() {
    }

    public static Kontekst getInstance() {
        return INSTANCE;
    }
    public static ServletContext kontekst = null;

    public ServletContext getKontekst() {
        return kontekst;
    }

    public void setKontekst(ServletContext kontekst) {
        Kontekst.kontekst = kontekst;
    }
}
