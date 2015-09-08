/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.testiranje;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.hrvoreski.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;

/**
 *
 * @author Hrvoje
 */
public class Testiranje {

    /**
     * Main metoda
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String sintaksa = "";
        Matcher m = null;
        boolean status = false;
        WebKonfiguracija config = null;
        //provjera upisanih argumenata
        switch (args[0]) {
            case "USER":
                // USER admin;  GET adresa;
                sintaksa = "^USER ([a-z0-9_-]{3,15});[\\s]*(GET[\\s]* ([a-zA-Z0-9 ]{3,25}));?$";
                m = provjeriRegex(sintaksa, args);
                status = m.matches();
                //u slučaju da je regex prošao za -user naredbu
                if (status) {

                    String adresa = "localhost";

                    String komanda = m.group(0).toString().trim();
                    try {
                        config = new WebKonfiguracija("NWTiS.db.config_1.xml");
                    } catch (NemaKonfiguracije ex) {
                        Logger.getLogger(Testiranje.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("ERROR - Ne postoji datoteka konfiguracije");
                    }
                    int port = config.getPort();
                    System.out.println("komnda" + komanda);
                    KlijentDretva kv = new KlijentDretva(adresa, config.getPort(), config, komanda);
                    kv.start();


                } else {
                    // USER admin;  GET ZIP 123456;
                    sintaksa = "^USER ([a-z0-9_-]{3,15});" //grupa 1
                    + "[\\s]*PASSWD[\\s]*([a-z0-9_-]{3,15})?;" //grupa 2
                    + "[\\s]*(PAUSE[\\s]*|START[\\s]*|STOP[\\s]*" //grupa 3 (
                    + "|ADD[\\s]* ([a-zA-Z0-9 ]{3,25})" //grupa 4
                    + "|TEST[\\s]* ([a-zA-Z0-9 ]{3,25})"//grupa 5
                    + "|GET[\\s]* ([a-zA-Z0-9 ]{3,25})" //grupa 6 )
                    + "|(ADD[\\s]* ([a-zA-Z0-9]{3,25});[\\s]*NEWPASSWD[\\s]* ([a-zA-Z0-9]{3,25}))" //grupa 7 - (8 i 9)
                    + ");?$";
                    m = provjeriRegex(sintaksa, args);
                    status = m.matches();
                    //u slučaju da je regex prošao za -user naredbu
                    if (status) {


                        String adresa = "localhost";
                        String komanda = m.group(0).toString().trim();
                        try {
                            config = new WebKonfiguracija("NWTiS.db.config_1.xml");
                        } catch (NemaKonfiguracije ex) {
                            Logger.getLogger(Testiranje.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("ERROR - Ne postoji datoteka konfiguracije");
                        }
                        int port = config.getPort();
                        System.out.println("komnda" + komanda);
                        KlijentDretva kv = new KlijentDretva(adresa, config.getPort(), config, komanda);
                        kv.start();
                        
                    } else {
                        System.out.println("Ne odgovara naredba");
                    }
                }
                break;
            default:
                System.out.println("Ne odgovara!");
                break;

        }
    }

    /**
     * Provjerava dobiveni string na temelju sintakse regexa
     *
     * @param sintaksa regex koji provjeravamo tipa String
     * @param args polje stringova nad kojim ćemo provjerit regex args[]
     * @return Matcher na kojem je izvršen pattern sintakse regexa
     */
    public static Matcher provjeriRegex(String sintaksa, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String p = sb.toString().trim();
        Pattern pattern = Pattern.compile(sintaksa);
        Matcher m = pattern.matcher(p);
        return m;
    }
    
}
