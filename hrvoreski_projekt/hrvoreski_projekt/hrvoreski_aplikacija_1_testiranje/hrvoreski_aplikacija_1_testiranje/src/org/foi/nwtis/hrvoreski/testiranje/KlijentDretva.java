/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.testiranje;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;

/**
 *
 * @author Hrvoje
 */
public class KlijentDretva extends Thread{

    private String ipServera;
    private int port;
    private WebKonfiguracija config;
    private String komanda;

    /**
     * Konstruktor dretve klijenta vremena
     *
     * @param ipServera ip adresa servera na koju se klijent spaja
     * @param port port kojega će klijent tražiti
     * @param korisnik username
     * @param config konfiguracijska datoteka
     */
    public KlijentDretva(String ipServera, int port, WebKonfiguracija config, String komanda) {
        this.ipServera = ipServera;
        this.port = port;
        this.config = config;
        this.komanda = komanda;
    }

    /**
     * Metoda koja pokreće dretvu klijenta vremena
     */
    @Override
    public synchronized void start() {
        super.start();//To change body of generated methods, choose Tools | Templates.

    }

    /**
     * Metoda izvršavanja dretve
     */
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        try {
            Socket server = new Socket(ipServera, port);
            OutputStream os = server.getOutputStream();
            InputStream is = server.getInputStream();
            
            System.out.println("KOMnda" + komanda);
            os.write(komanda.getBytes());
            os.flush();
            server.shutdownOutput();
            StringBuilder odgovor = new StringBuilder();
            while (true) {
                int znak = is.read();
                if (znak == -1) {
                    break;

                } else {
                    odgovor.append((char) znak);
                }

            }
            System.out.println("Dretva:" + getName() + " Odgovor je:" + odgovor);
            String serverTime = "";
            if (odgovor.toString().startsWith("OK")) {
                System.out.println(odgovor);
            }

            os.close();
            is.close();
            server.close();

        } catch (IOException ex) {
            System.out.println("Nema odgovora.");
        }
        this.interrupt();

    }

    /**
     * Prekida izvrsavanje dretve
     */
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }
}
