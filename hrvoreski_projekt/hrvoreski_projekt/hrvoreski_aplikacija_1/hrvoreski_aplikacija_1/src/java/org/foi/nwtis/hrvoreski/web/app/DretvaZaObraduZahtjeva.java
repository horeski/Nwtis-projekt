/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.hrvoreski.helperi.Brojac;
import org.foi.nwtis.hrvoreski.helperi.Dnevnik;
import org.foi.nwtis.hrvoreski.helperi.HelperZaMail;
import org.foi.nwtis.hrvoreski.helperi.HelperZaZahtjeve;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.kontrole.PorukaProsireno;

/**
 * Kada dođe zahtjev na socket, ova dretva obavlja obradu tih zahtjeva te
 * temeljem propisanog radi određene akcije, na kraju svakog ciklusa šalje mail
 * o statistici
 *
 * @author Hrvoje
 */
public class DretvaZaObraduZahtjeva extends Thread {

    private WebKonfiguracija config;
    private Socket klijent;
    private Brojac brojac;
    private HelperZaZahtjeve helper;
    private HelperZaMail helperMail;
    private String komanda;
    private Date vrijemePrimanjaNaredbe;
    private String korisnik;
    private boolean zaustavljena;
    private boolean autentificiran;
    private boolean obicanKorisnik;
    private int statusOdgovora;
    private Dnevnik dnevnik;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
    private String id = "NWTiS" + String.valueOf(new Date().getTime());
    private String vrstaPoruke = "text/plain; charset=UTF-8";

    DretvaZaObraduZahtjeva(WebKonfiguracija config, Socket klijent, Brojac brojac, HelperZaZahtjeve helper, HelperZaMail helperMail) {
        this.config = config;
        this.klijent = klijent;
        this.brojac = brojac;
        this.helper = helper;
        vrijemePrimanjaNaredbe = new Date();
        this.setName("Obrada zahtjeva : " + vrijemePrimanjaNaredbe.toString());
        this.zaustavljena = false;
        this.autentificiran = false;
        this.statusOdgovora = 0;
        this.helperMail = helperMail;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * Logika za obradu zahtjeva
     */
    @Override
    public void run() {

        super.run();
        String komandaKorisnik = "";
        String komandaLozinka = "";
        String primljenaNaredba = "";
        String usernameNovo = "";
        String lozinkaNovo = "";
        String adresa = "";
        String odgovor = "";

        System.out.println("Klijent je uspješno spojen");

        try (InputStream is = klijent.getInputStream();
                OutputStream os = klijent.getOutputStream();
                InputStreamReader isr = new InputStreamReader(is, "ISO-8859-2")) {
            StringBuilder obradaKomande = new StringBuilder();
            while (true) {
                int znak = isr.read();
                if (znak == -1) {
                    break;
                } else {
                    obradaKomande.append((char) znak);
                }
            }
            brojac.brojPrimljenihKomandi.getAndIncrement();
            System.out.println("Primljena komanda: " + obradaKomande);
            komanda = obradaKomande.toString();
            vrijemePrimanjaNaredbe = new Date();
            String regexSintaksa = "^USER ([a-z0-9_-čćžšđĐČĆŽĐŠ]{3,15});" //grupa 1
                    + "[\\s]*PASSWD[\\s]*([a-z0-9_-čćžšđĐČĆŽĐŠ]{3,15})?;" //grupa 2
                    + "[\\s]*(PAUSE[\\s]*|START[\\s]*|STOP[\\s]*" //grupa 3 (
                    + "|ADD[\\s]* ([a-zA-Z0-9čćžšđĐČĆŽĐŠ., ]{3,25})" //grupa 4
                    + "|TEST[\\s]* ([a-zA-Z0-9čćžšđĐČĆŽĐŠ., ]{3,25})"//grupa 5
                    + "|GET[\\s]* ([a-zA-Z0-9čćžšđĐČĆŽĐŠ., ]{3,25})" //grupa 6 )
                    + "|(ADD[\\s]* ([a-zA-Z0-9čćžšđĐČĆŽĐŠ]{3,25});[\\s]*NEWPASSWD[\\s]* ([a-zA-Z0-9čćžšđĐČĆŽĐŠ]{3,25}))" //grupa 7 - (8 i 9)
                    + ");?$";
            String p = komanda.trim(); //String p = obradaKomande.toString().trim();
            Pattern pattern = Pattern.compile(regexSintaksa);
            Matcher m = pattern.matcher(p);
            boolean status = m.matches();
            //regex je prošao i dohvatit će sad sve parametre
            if (status) {
                korisnik = m.group(1);
                komandaKorisnik = m.group(1);
                komandaLozinka = m.group(2);
                if (m.group(4) == null && m.group(5) == null && m.group(6) == null && m.group(7) == null) {
                    primljenaNaredba = m.group(3);
                } else {
                    if (m.group(3).startsWith("ADD") && m.group(7) == null) {
                        adresa = m.group(4);
                        primljenaNaredba = "ADD " + adresa;
                    } else if (m.group(3).startsWith("TEST")) {
                        adresa = m.group(5);
                        primljenaNaredba = "TEST " + adresa;
                    } else if (m.group(3).startsWith("GET")) {
                        adresa = m.group(6);
                        primljenaNaredba = "GET " + adresa;
                    } else if (m.group(3).startsWith("ADD") && m.group(7) != null) {
                        usernameNovo = m.group(8);
                        lozinkaNovo = m.group(9);
                        primljenaNaredba = "ADD " + usernameNovo + "; NEWPASSWD " + lozinkaNovo;
                    } else {
                        primljenaNaredba = "";
                    }
                }
                System.out.println("Dobiveni parametri za: " + komandaKorisnik + ", s lozinkom " + komandaLozinka + ", naredba: " + primljenaNaredba);
            } else {  // pokušaj za obične korisnike
                regexSintaksa = "^USER ([a-z0-9_-čćžšđĐČĆŽĐŠ]{3,15});[\\s]*(GET[\\s]* ([a-zA-Z0-9čćžšđĐČĆŽĐŠ., ]{3,25}));?$";
                pattern = Pattern.compile(regexSintaksa);
                m = pattern.matcher(p);
                status = m.matches();
                if (status) {
                    korisnik = m.group(1);
                    komandaKorisnik = m.group(1);
                    adresa = m.group(3);
                    primljenaNaredba = "GET " + adresa;
                    System.out.println("Postavaljam običnog!");
                    obicanKorisnik = true;
                }
            }

            if (MeteoPreuzimanje.isPauza() && !(primljenaNaredba.equalsIgnoreCase("START"))) {
                odgovor = "ERR 40";
            } else if (!helper.provjeraNadimkaKorisnika(config, korisnik).equalsIgnoreCase("OK")) {
                if (primljenaNaredba.equals("GET " + adresa) && obicanKorisnik == true) {
                    System.out.println("Običan korisnik");
                    odgovor = helper.getMeteoPodatkeZaAdresu(config, adresa);
                    if (odgovor.equalsIgnoreCase("OK 10")) {
                        statusOdgovora = 1;
                    } else {
                        statusOdgovora = 0;
                    }
                } else {
                    odgovor = "ERR 30";
                }
            } else {
                //provjera da li je korisnik verificiran kao admin
                String provjera = helper.provjeraAdmina(config, komandaKorisnik, komandaLozinka);
                if (provjera.equalsIgnoreCase("OK")) {
                    System.out.println("Admin");
                    autentificiran = true;
                } else {
                    autentificiran = false;
                }

                //admin dio
                if (autentificiran) {
                    //zaustavi preuzimati meteo podatke
                    if ("STOP".equals(primljenaNaredba)) {
                        if (!MeteoPreuzimanje.isKraj()) {
                            MeteoPreuzimanje.setKraj(true);
                            odgovor = "OK 10";
                            statusOdgovora = 1;
                            System.out.println("GASIM ");
                        } else {
                            odgovor = "ERR 42";
                            statusOdgovora = 0;
                        }
                        //pauziraj preuzimanje meteo podataka
                    } else if ("PAUSE".equals(primljenaNaredba)) {
                        if (MeteoPreuzimanje.isPauza()) {
                            odgovor = "ERR 40";
                            statusOdgovora = 0;

                        } else {
                            MeteoPreuzimanje.setPauza(true);
                            odgovor = "OK 10";
                            statusOdgovora = 1;
                            System.out.println("PAUZA ");
                        }
                        //nastavljaj preuzimati meteo podatke
                    } else if ("START".equals(primljenaNaredba)) {
                        if (MeteoPreuzimanje.isPauza()) {
                            MeteoPreuzimanje.setPauza(false);
                            odgovor = "OK 10";
                            statusOdgovora = 1;
                            System.out.println("START ");
                        } else {
                            odgovor = "ERR 41";
                            statusOdgovora = 0;
                        }
                        //dodavanje nove adrese
                    } else if (primljenaNaredba.equals("ADD " + adresa)) {
                        odgovor = helper.dodajNovuAdresu(config, adresa, korisnik);
                        if (odgovor.equalsIgnoreCase("OK 10")) {
                            statusOdgovora = 1;
                        } else {
                            statusOdgovora = 0;
                        }
                        //testiranje adrese
                    } else if (primljenaNaredba.equals("TEST " + adresa)) {
                        odgovor = helper.testirajAdresu(config, adresa);
                        if (odgovor.equalsIgnoreCase("OK 10")) {
                            statusOdgovora = 1;
                        } else {
                            statusOdgovora = 0;
                        }
                        //dohvaćanje meteo podataka za adresu
                    } else if (primljenaNaredba.equals("GET " + adresa)) {
                        odgovor = helper.getMeteoPodatkeZaAdresu(config, adresa);
                        if (odgovor.equalsIgnoreCase("OK 10")) {
                            statusOdgovora = 1;
                        } else {
                            statusOdgovora = 0;
                        }
                        //dodavanje korisnika   
                    } else if (primljenaNaredba.equals("ADD " + usernameNovo + "; NEWPASSWD " + lozinkaNovo)) {
                        odgovor = helper.dodajKorisnika(config, usernameNovo, lozinkaNovo);
                        if (odgovor.equalsIgnoreCase("OK 10")) {
                            statusOdgovora = 1;
                        } else {
                            statusOdgovora = 0;
                        }
                    } else {
                        //naredba koja ne postoji!
                        odgovor = "ERR 30";
                        statusOdgovora = 0;
                    }

                } else {
                    //naredba koja ne postoji i nije verificiran
                    odgovor = "ERR 30";
                    statusOdgovora = 0;
                }

            }
            //klijentu se šalje odgovor i zatvara se veza
            //osw.write(odgovor.getBytes("ISO-8859-2"));
            os.write(odgovor.getBytes("ISO-8859-2"));
            os.close();
            isr.close();

        } catch (IOException ex) {
            Logger.getLogger(DretvaZaObraduZahtjeva.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (statusOdgovora == 1) {
            brojac.brojIzvrsenihKomandi.incrementAndGet();
        } else {
            brojac.brojNeispravnihKomandi.incrementAndGet();
        }

        dnevnik = new Dnevnik(config);
        dnevnik.zapisiDnevnik(sdf.format(vrijemePrimanjaNaredbe), korisnik, komanda, odgovor);
        if (brojac.trajanjePrethodnogStanja.get() == 0) {
            brojac.trajanjePrethodnogStanja.set(new Date().getTime());
        }
        if (autentificiran) {
            System.out.println("ŠALJE MAIL");
            String sadrzaj = "";
            sadrzaj = "Komanda: " + komanda + "\n"
                    + "Vrijeme izvršavanja: " + sdf.format(vrijemePrimanjaNaredbe) + "\n"
                    + "Trajanje prethodnog stanja: " + String.valueOf(sdf.format(new Date(brojac.trajanjePrethodnogStanja.get()))) + "\n"
                    + "Broj primljenih naredbi: " + String.valueOf(brojac.brojPrimljenihKomandi.get()) + "\n"
                    + "Broj neispravnih naredbi: " + String.valueOf(brojac.brojNeispravnihKomandi.get()) + "\n"
                    + "Broj izvršenih naredbi: " + String.valueOf(brojac.brojIzvrsenihKomandi.get()) + "\n";
            
            System.out.println("MAIL SADRŽAJ " + sadrzaj);
            //slanje poruke      
            PorukaProsireno poruka = new PorukaProsireno(id, new Date(), config.getKorisnickoIme(),
                    config.getTrazeniPredmet(), vrstaPoruke, sadrzaj.length(), 0, null, null, false, false, sadrzaj);
            brojac.trajanjePrethodnogStanja.set(vrijemePrimanjaNaredbe.getTime());
            helperMail.posaljiMail(config.getAdresaPosluzitelja(), config.getKorisnickoIme(), config.getLozinkaKorisnika(), config.getDirPoslanePoruke(), config.getPrimateljEmailPoruke(), poruka);

        }
        zaustavljena = true;
        try {
            klijent.close();
        } catch (IOException ex) {
            Logger.getLogger(DretvaZaObraduZahtjeva.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    public boolean isZaustavljena() {
        return zaustavljena;
    }
}
