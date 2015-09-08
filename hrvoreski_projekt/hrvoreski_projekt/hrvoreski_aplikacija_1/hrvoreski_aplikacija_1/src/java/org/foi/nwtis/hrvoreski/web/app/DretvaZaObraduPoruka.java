/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.hrvoreski.helperi.Brojac;
import org.foi.nwtis.hrvoreski.helperi.HelperZaMail;
import org.foi.nwtis.hrvoreski.helperi.HelperZaZahtjeve;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;

/**
 * Glavna dretva koja pokreće i preuzimanje meteo podataka koje radi cijelo vrijeme po zadanom intervalu,
 * dok očekuje i poziv na socket s zadanom komandom i neometano o meteo dretvi vrši obradu zahtjeva
 * uz pomoć druge dretve
 * @author Hrvoje Oreški
 */
public class DretvaZaObraduPoruka extends Thread {

    public boolean kraj = false;
    private WebKonfiguracija config;
    private ServerSocket server;
    private Socket klijent;
    private MeteoPreuzimanje mp;
    private boolean radi = true;

    /**
     * Konstruktor za preuzimanje meteo podataka
     *
     * @param config konfiguracijska datoteka
     * @param server broj porta za server
     */
    public DretvaZaObraduPoruka(WebKonfiguracija config, ServerSocket server) {
        this.server = server;
        this.config = config;
        this.setName("DretvaZaObraduPoruka");
    }

    /**
     * Pokreće preuzimanje meteo podataka otvaranjem socketa i čeka na klijente
     */
    public void pokreniPreuzimanjeMeteoPodataka() {
        //pokreni dretvu za meteo podatke
        mp = new MeteoPreuzimanje(config);
        mp.pokreniPreuzimanje();
    }
    /**
     * Pokreni metodu koja će preuzimati meteo podatke
     */
    @Override
    public synchronized void start() {
        super.start();
        pokreniPreuzimanjeMeteoPodataka();
        System.out.println("Preuzimanje podataka pokrenuto !");
    }
    
    /**
     * Dohvaća socket na portu iz konfiguracije, zatim dok god app nije zaustavljena 
     * preuzimaju se podaci i prihvaćaju naredbe na zadanom portu (poziva se dretva za obradu zahtjeva)
     */
    @Override
    public void run() {
        try {
            super.run();
            //dohvati socket na portu konfiguracije
            Brojac brojac = Brojac.getInstance();
            HelperZaZahtjeve helper = new HelperZaZahtjeve();
            HelperZaMail helperMail = new HelperZaMail();
            List<DretvaZaObraduZahtjeva> listaDretvi = new ArrayList<>();
            //dok aplikacija nije zaustavljena preuzimaj podatke
            while(!MeteoPreuzimanje.isKraj()){
                klijent = server.accept();
                DretvaZaObraduZahtjeva dzoz = new DretvaZaObraduZahtjeva(config, klijent, brojac, helper, helperMail);
                dzoz.setName("DretvaZaObraduZahtjeva " + new Date().toString());
                dzoz.start();
                
                listaDretvi.add(dzoz);
                for(DretvaZaObraduZahtjeva thread : listaDretvi){
                    if(thread.isZaustavljena()){
                        thread.interrupt();           
                    }            
                }
                if(!radi){
                    MeteoPreuzimanje.setKraj(true);
                    break;
                }               
            }
            klijent.close();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(DretvaZaObraduPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void interrupt() {
        super.interrupt();
        try {
            if (klijent != null && !klijent.isClosed()) {
                klijent.close();
            }
            if (server != null && !server.isClosed()) {
                server.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(DretvaZaObraduPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
        MeteoPreuzimanje.setKraj(true);
        mp.zaustaviPreuzimanje();
    }
}
