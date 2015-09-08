/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.helperi;

import java.util.Date;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.hrvoreski.kontrole.PorukaProsireno;

/**
 * Pomoćna klasa za slanje maila
 *
 * @author Hrvoje
 */
public class HelperZaMail {

    /**
     * Metoda za slanje email poruke
     *
     * @param emailPosluzitelj String posluzitelja na koji se salje poruka
     * @param emailKorisnik String email adrese korisnika koji salje poruku
     * @param emailLozinka String lozinke korisnika koji salje poruku
     * @param sentFolderName String mape u koju ce se premjestiti poslana poruka
     * @param primatelj String email adresa korisnika koji prima poruku
     * @param poruka PorukaProsirena varijabla koja se salje
     * @return true u slucaju uspjesnog slanja poruke
     */
    public synchronized boolean posaljiMail(String emailPosluzitelj, String emailKorisnik, String emailLozinka, String sentFolderName, String primatelj, PorukaProsireno poruka) {
        // Recipient's email ID needs to be mentioned.
        String to = primatelj;

        // Sender's email ID needs to be mentioned
        String from = poruka.getSalje();


        Session session = Session.getDefaultInstance(System.getProperties(), null);
        //getting the session for accessing email

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // zaglavlje from
            message.setFrom(new InternetAddress(from));

            // zaglavlje primatelj
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setHeader("Content-Type", poruka.getVrsta());
            // subject
            message.setSubject(poruka.getPredmet());


            // postavi sadrzaj poruke
            message.setContent(poruka.getSadrzaj(), poruka.getVrsta());

            message.setSentDate(new Date());

            // posalji poruku
            Transport.send(message);
            // dohvati session store
            Store store = session.getStore("imap");
            // spoji se na store
            //Connection established with IMAP server
            store.connect(emailPosluzitelj, emailKorisnik, emailLozinka);

            // Get a handle on the default folder
            Folder folder = store.getDefaultFolder();
            // dohvati folder statistike
            folder = folder.getFolder(sentFolderName);
            // ako ne postoji folder kreiraj ga
            if (!folder.exists()) {
                folder.create(Folder.HOLDS_MESSAGES);
            }
            // dodaj poruke u poslane poruke statistike
            folder.open(Folder.READ_WRITE);
            folder.appendMessages(new Message[]{message});
            folder.close(true);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

}
