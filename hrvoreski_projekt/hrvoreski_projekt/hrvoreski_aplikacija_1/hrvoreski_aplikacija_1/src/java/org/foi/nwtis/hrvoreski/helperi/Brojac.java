/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.helperi;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Broji primljene naredbe, komande, trajanje određenog stanja, te konkurentnog
 * pristupa
 *
 * @author Hrvoje
 */
public class Brojac {

    private static final Brojac INSTANCE = new Brojac();

    // Private constructor prevents instantiation from other classes
    private Brojac() {
    }

    public static Brojac getInstance() {
        return INSTANCE;
    }
    public AtomicInteger brojPrimljenihKomandi = new AtomicInteger(0);
    public AtomicInteger brojNeispravnihKomandi = new AtomicInteger(0);
    public AtomicInteger brojIzvrsenihKomandi = new AtomicInteger(0);
    public AtomicLong trajanjePrethodnogStanja = new AtomicLong(0);

}
