/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.slusaci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.sb.MessagesStorage;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSAdresaStruktura;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSPorukaStruktura;

/**
 * Web application lifecycle listener.
 *
 * @author Hrvoje
 */
public class SlusacAplikacije implements ServletContextListener {

    MessagesStorage messagesStorage = lookupMessagesStorageBean();
    private WebKonfiguracija config = null;
    private String datotekaAdrese;
    private String datotekaEmail;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String path = sce.getServletContext().getRealPath("WEB-INF");
        String datoteka = sce.getServletContext().getInitParameter("config");
        config = new WebKonfiguracija(path + File.separator + datoteka);
        sce.getServletContext().setAttribute("konfiguracija", config);
        datotekaAdrese = config.getDatotekaAdrese();
        datotekaEmail = config.getDatotekaEmail();
        messagesStorage.setEmailPoruke(ucitajDatotekuMail(datotekaEmail));
        messagesStorage.setAdresaPoruke(ucitajDatoteku(datotekaAdrese));

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        spremiListu(messagesStorage.getAdresaPoruke(), datotekaAdrese);
        spremiListuMail(messagesStorage.getEmailPoruke(), datotekaEmail);
    }

    /**
     * Učitava datoteku evidencije za adrese
     *
     * @param datoteka iz nje se čita
     * @return lista adresa
     */
    public List<JMSAdresaStruktura> ucitajDatoteku(String datoteka) {
        List<JMSAdresaStruktura> lista = new ArrayList<>();

        System.out.println("Učitavam datoteku: " + datoteka);
        try {
            FileInputStream in = new FileInputStream(datoteka);
            ObjectInputStream ois = new ObjectInputStream(in);

            while (in.available() != 0) {
                JMSAdresaStruktura poruka = (JMSAdresaStruktura) ois.readObject();
                lista.add(poruka);
            }
            ois.close();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Sprema datoteku evidencije za adrese
     *
     * @param lista ulazna lista
     * @param datoteka izlazna datoteka
     */
    public void spremiListu(List<JMSAdresaStruktura> lista, String datoteka) {
        if (!lista.isEmpty()) {
            File dat = new File(datoteka);

            try {
                System.out.println("Spremam datoteku: " + datoteka);
                FileOutputStream out = new FileOutputStream(dat);
                ObjectOutputStream oos = new ObjectOutputStream(out);

                for (int i = 0; i < lista.size(); i++) {
                    oos.writeObject(lista.get(i));
                }
                oos.close();
                out.close();
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        } else {
            System.out.println("Lista adresa je prazna.");
        }
    }
    /**
     * Učitava evidenciju za mail
     * @param datoteka datoteka iz koje se čita evid
     * @return listu mailova iz evid
     */
    public List<JMSPorukaStruktura> ucitajDatotekuMail(String datoteka) {
        List<JMSPorukaStruktura> lista = new ArrayList<>();
        System.out.println("Učitavam datoteku: " + datoteka);
        try {
            FileInputStream in = new FileInputStream(datoteka);
            ObjectInputStream ois = new ObjectInputStream(in);
            while (in.available() != 0) { //ako postoji jos bajtova za citati
                JMSPorukaStruktura poruka = (JMSPorukaStruktura) ois.readObject();
                lista.add(poruka);
            }
            ois.close();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        }
        return lista;
    }
    /**
     * Sprema listu mailova u evid
     * @param lista ulazna lista
     * @param datoteka u nju zapisuje
     */
    public void spremiListuMail(List<JMSPorukaStruktura> lista, String datoteka) {
        File dat = new File(datoteka);
        try {
            System.out.println("Spremam datoteku: " + datoteka);
            FileOutputStream out = new FileOutputStream(dat);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            for (int i = 0; i < lista.size(); i++) {
                oos.writeObject(lista.get(i));
            }
            oos.close();
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private MessagesStorage lookupMessagesStorageBean() {
        try {
            Context c = new InitialContext();
            return (MessagesStorage) c.lookup("java:global/hrvoreski_aplikacija_3/hrvoreski_aplikacija_3_2/MessagesStorage!org.foi.nwtis.hrvoreski.sb.MessagesStorage");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
