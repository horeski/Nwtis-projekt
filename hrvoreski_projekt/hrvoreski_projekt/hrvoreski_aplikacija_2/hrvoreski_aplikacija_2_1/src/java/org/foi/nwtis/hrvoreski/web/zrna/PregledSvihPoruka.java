/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.kontrole.PorukaProsireno;
import org.foi.nwtis.hrvoreski.web.kontrole.PrivitakPoruke;

/**
 * Managed bean koji prikazuje sve poruke odabrane mape email servera
 *
 * @author Hrvoje
 */
@ManagedBean
@SessionScoped
public class PregledSvihPoruka {

    private String adresaPosluzitelja;
    private String korisnickoIme;
    private String lozinka;
    private List<PorukaProsireno> poruke = new ArrayList<PorukaProsireno>();
    private PorukaProsireno poruka;
    private int pomak;
    private int brojUcitanihPoruka;
    private String errorMessage = "";
    private int odabranaStranica;
    private int maxStranica;
    private String porukaID;
    private String odabraniFolder = "inbox";
    private List<String> folderi = new ArrayList();

    /**
     * Konstruktor klase
     */
    public PregledSvihPoruka() {
        this.pomak = 0;
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        WebKonfiguracija konfig = (WebKonfiguracija) ctx.getAttribute("konfiguracija");
        adresaPosluzitelja = konfig.getAdresaPosluzitelja();
        korisnickoIme = konfig.getKorisnickoIme();
        lozinka = konfig.getLozinkaKorisnika();
        this.brojUcitanihPoruka = konfig.getBrojUcitanihPoruka();
        odabraniFolder = "inbox";
        errorMessage = "";
        maxStranica = inicijalizirajMaxStranice(odabraniFolder);
    }

    /**
     * Dohvatit će maksimalan broj stranica foldera te tako će zaobići greške
     * koje bi mogle nastati prilikom straničenja!
     *
     * @param odabraniFolder odabrani folder emaila
     * @return broj max stranica foldera emaila
     */
    private int inicijalizirajMaxStranice(String odabraniFolder) {

        try {
            Session session = Session.getDefaultInstance(System.getProperties(), null);
            Store store;
            store = session.getStore("imap");
            store.connect(adresaPosluzitelja, korisnickoIme, lozinka);
            Folder folder = store.getDefaultFolder();
            folder = folder.getFolder(odabraniFolder);
            int ukupnoPoruka = folder.getMessageCount();
            return (int) Math.ceil(ukupnoPoruka / brojUcitanihPoruka);
        } catch (MessagingException ex) {
            return 0;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getOdabraniFolder() {
        return odabraniFolder;
    }

    public void setOdabraniFolder(String odabraniFolder) {
        this.odabraniFolder = odabraniFolder;
    }

    public String odaberiMapu() {
        return "";
    }

    public void setFolderi(List<String> folderi) {
        this.folderi = folderi;
    }

    public int getOdabranaStranica() {
        return odabranaStranica;
    }

    public void setOdabranaStranica(int odabranaStranica) {
        this.odabranaStranica = odabranaStranica;
    }

    public int getMaxStranica() {
        return maxStranica;
    }

    public void setMaxStranica(int maxStranica) {
        this.maxStranica = maxStranica;
    }

    public List<PorukaProsireno> getPoruke() {
        obradiMail();
        return poruke;
    }

    public void setPoruke(List<PorukaProsireno> poruke) {
        this.poruke = poruke;
    }

    public PorukaProsireno getPoruka() {
        return poruka;
    }

    public void setPoruka(PorukaProsireno poruka) {
        this.poruka = poruka;
    }

    public String getPorukaID() {
        return porukaID;
    }

    public void setPorukaID(String porukaID) {
        poruka = null;
        for (PorukaProsireno p : poruke) {
            if (p.getId().equals(porukaID)) {
                poruka = p;
                break;
            }
        }
        this.porukaID = porukaID;
    }

    public List<String> getFolderi() {
        try {
            Session session = null;
            Store store = null;
            Folder folder = null;
            Message message = null;
            Message[] messages = null;
            Object messagecontentObject = null;
            String sender = null;
            String subject = null;
            Multipart multipart = null;
            Part part = null;
            String contentType = null;
            String sadrzaj = "";

            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");
            store.connect(adresaPosluzitelja, korisnickoIme, lozinka);

            //defaultni folder
            folder = store.getDefaultFolder();
            javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
            folderi.clear();
            for (javax.mail.Folder f : folders) {
                if ((f.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
                    folderi.add(f.getName());
                }
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(PregledSvihPoruka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledSvihPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return folderi;
    }

    /**
     * Metoda koja će odvesti korisnika na pregled odabrane poruke
     *
     * @return ukoliko postoji poruka vraća se odgovor OK, inače NOT_OK, ERROR
     */
    public String pregledPoruke() {
        if (poruka == null) {
            errorMessage = "Poruka ne postoji !";
            return "ERROR";
        } else if (!poruke.contains(poruka)) {
            errorMessage = "Poruka ne postoji !";
            return "NOT_OK";
        } else {
            errorMessage = "";
            return "OK";
        }
    }

    public void dodajBrojStranice() {
        if (odabranaStranica >= maxStranica) {
            odabranaStranica = maxStranica;
        } else {
            this.odabranaStranica++;
        }
    }

    public void oduzmiBrojStranice() {
        this.odabranaStranica--;
        if (this.odabranaStranica <= 0) {
            this.odabranaStranica = 0;
        }
    }

    /**
     * Metoda koja će obraditi mailove, tj za odabranu mapu i spremit će ih u
     * listu za prikaz
     */
    public void obradiMail() {
        Session session = null;
        Store store = null;
        Folder folder = null;
        Message message = null;
        Message[] messages = null;
        Object messagecontentObject = null;
        String sender = null;
        String subject = null;
        Multipart multipart = null;
        Part part = null;
        String contentType = null;
        String sadrzaj = "";
        try {
            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");
            store.connect(adresaPosluzitelja, korisnickoIme, lozinka);
            //rad s default folderom
            folder = store.getDefaultFolder();
            //dohvati inbox
            folder = folder.getFolder(odabraniFolder);
            //čitaj inbox u read mode-u
            folder.open(Folder.READ_ONLY);
            poruke.clear();
            //dohvatit će poruke, te odrediti broj učitanih poruka, usput srediti varijable za straničenje
            int ukupnoPoruka = folder.getMessageCount();
            int ukupnoStranica = (int) Math.ceil(ukupnoPoruka / brojUcitanihPoruka);
            maxStranica = ukupnoStranica;
            int od = odabranaStranica * brojUcitanihPoruka + 1;
            int doBroja = ((odabranaStranica * brojUcitanihPoruka + 1) + brojUcitanihPoruka);
            if (doBroja > ukupnoPoruka) {
                doBroja = ukupnoPoruka;
            }
            //ukoliko postoji poruka u folderu obavi stranicenje
            if (doBroja > 0) {
                messages = folder.getMessages(od, doBroja);
            } else {
                //ako nema poruka neka vrati praznu listu
                messages = folder.getMessages();
            }

            //petlja kroz sve poruke
            for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                //dohvati slijedeću poruku za čitanje
                message = messages[messageNumber];
                sadrzaj = "";
                //dohvati sadržaj poruke
                messagecontentObject = message.getContent();
                List<PrivitakPoruke> attachments = new ArrayList<>();
                //odredi vrstu mail-a
                if (messagecontentObject instanceof Multipart) {
                    sender = ((InternetAddress) message.getFrom()[0]).getPersonal();
                    //ako osobna (personal) informacija nema unosa, provjeri adresu za info o senderu
                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                    }
                    //dohvati informaciju o subjektu
                    subject = message.getSubject();
                    //dohvati multipart objekt iz poruke
                    multipart = (Multipart) message.getContent();
                    //prođi kroz sve dijelove mail-a
                    for (int i = 0; i < multipart.getCount(); i++) {
                        //dohvati idući dio
                        part = multipart.getBodyPart(i);
                        //dohvati tip sadržaja
                        contentType = part.getContentType().toLowerCase();
                        //prikaži tip sadržaja
                        String fileName = "";
                        if (contentType.startsWith("text/plain")) {
                            sadrzaj = part.getContent().toString();
                        } else {
                            //dohvati ime file-a
                            fileName = part.getFileName();
                        }
                        if ((contentType.toLowerCase().startsWith("image/*") || contentType.toLowerCase().startsWith("application/octet-stream"))) {
                            PrivitakPoruke privitak = new PrivitakPoruke(i, contentType, part.getSize(), fileName);
                            attachments.add(privitak);
                        }
                    }
                } else {
                    sender = ((InternetAddress) message.getFrom()[0]).getPersonal();
                    //ako osobna (personal) informacija nema unosa, provjeri adresu sendera
                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                    }
                    //dohvati info o subjektu
                    subject = message.getSubject();
                    sadrzaj = message.getContent().toString();
                    contentType = message.getContentType();
                }
                String[] zaglavlja = message.getHeader("Message-ID");
                String messageId = "";
                if (zaglavlja != null && zaglavlja.length > 0) {
                    messageId = zaglavlja[0];
                }
                PorukaProsireno p = new PorukaProsireno(messageId, message.getSentDate(), subject, sender, contentType,
                        message.getSize(), attachments.size(), message.getFlags(),
                        attachments, true, true, sadrzaj);
                poruke.add(p);
            }
            //zatvori folder tj mapu
            folder.close(true);
            //zatvori store poruka
            store.close();
        } catch (AuthenticationFailedException e) {
            System.out.println(e.toString());
        } catch (MessagingException | IOException e) {
            System.out.println(e.toString());
        }
    }
}
