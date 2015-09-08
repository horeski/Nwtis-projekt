/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.mb;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.hrvoreski.sb.MessagesStorage;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSAdresaStruktura;

/**
 * Message bean koji prima poruke JMS adrese objekta
 *
 * @author Hrvoje
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_hrvoreski_1")
})
public class AdreseMessageBean implements MessageListener {

    @EJB
    private MessagesStorage messagesStorage;

    @Resource(name = "korisnickoIme")
    private String korisnik;

    @Resource(name = "lozinka")
    private String lozinka;

    @Resource(name = "adresaPosluzitelja")
    private String adresaPosluzitelja;

    @Resource(name = "port")
    private Integer port;

    /**
     * Konstruktor
     */
    public AdreseMessageBean() {
    }

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println("Pristigla nova JMS poruka za adresu !");
            ObjectMessage o = (ObjectMessage) message;
            JMSAdresaStruktura poruka = (JMSAdresaStruktura) o.getObject();
            poruka.setBrisi(messagesStorage.getCounter());
            messagesStorage.dodajAdresu(poruka);

            String adresa = poruka.getAdresa();
            System.out.println("APP 3 - JMS ZA ADRESU " + adresa);
            if (adresa != null) {
                String zahtjevTest = "USER " + korisnik + "; PASSWD " + lozinka + "; TEST " + adresa + ";";
                String odgovor = saljiKomandu(zahtjevTest);
                System.out.println("APP 3 - odgovor za TEST : " + odgovor);

                if (odgovor.equals("ERR 51")) {
                    String zahtjevAdd = "USER " + korisnik + "; PASSWD " + lozinka + "; ADD " + adresa + ";";
                    odgovor = saljiKomandu(zahtjevAdd);
                    System.out.println("APP 3 - odgovor za ADD : " + odgovor);
                }
            }

        } catch (JMSException ex) {
            Logger.getLogger(AdreseMessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String saljiKomandu(String zahtjev) {
        Socket server;
        try {
            server = new Socket(adresaPosluzitelja, port);
            InputStream is = server.getInputStream();
            OutputStream os = server.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is, "ISO-8859-2");
            OutputStreamWriter osw = new OutputStreamWriter(os, "ISO-8859-2");
            osw.write(zahtjev);
            osw.flush();
            server.shutdownOutput();

            //dohvati odgovor
            StringBuilder odgovor = new StringBuilder();
            while (true) {
                int znak = isr.read();
                if (znak == -1) {
                    break;
                }
                odgovor.append((char) znak);
            }
            isr.close();
            osw.close();
            is.close();
            os.close();
            server.close();
            return odgovor.toString();
        } catch (ConnectException ex) {
            return "veza odbijena";
        } catch (IOException ex) {
            Logger.getLogger(AdreseMessageBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
