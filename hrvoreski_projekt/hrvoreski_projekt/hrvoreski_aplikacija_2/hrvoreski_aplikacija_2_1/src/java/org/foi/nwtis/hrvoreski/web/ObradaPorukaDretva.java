/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.kontrole.JMSPorukaStruktura;

/**
 * Dretva koja se bavi obradom poruka i njihovim razvrstavanjem po mapama uz to
 * prihvaća privitke te ih u slucaju autentifikacije razvrsta po mapama
 * korisnika, te na kraju svakog intervala se šalje JMS poruka za emailove!
 *
 * @author Hrvoje
 */
public class ObradaPorukaDretva extends Thread {

    //podaci za mail i sl
    private boolean radi;
    private WebKonfiguracija konfig;
    private int interval;
    public static String emailPosluzitelj;
    private String emailPort;
    public static String emailKorisnik;
    private String emailLozinka;
    private String trazeniPredmet;
    ServletContext context = null;

    //brojaci
    private int sveukupnoPoruka = 0;
    private int ukupnoPoruka;
    private int ispravnihPoruka;
    private int ostalihPoruka;

    //mape
    private Folder ispravnePorukeDir = null;
    private Folder ostalePorukeDir = null;

    //stat
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss");
    private Date startObrade;
    private Date endObrade;

    public boolean isRadi() {
        return radi;
    }

    public void setRadi(boolean radi) {
        this.radi = radi;
    }

    public WebKonfiguracija getKonfig() {
        return konfig;
    }

    public void setKonfig(WebKonfiguracija konfig) {
        this.konfig = konfig;
    }

    /**
     * inicijalizacija parametara
     */
    @Override
    public synchronized void start() {
        radi = true;
        context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        setKonfig((WebKonfiguracija) context.getAttribute("konfiguracija"));
        interval = konfig.getInterval();
        emailPosluzitelj = konfig.getAdresaPosluzitelja();
        emailPort = konfig.getEmailPort();
        emailKorisnik = konfig.getKorisnickoIme();
        emailLozinka = konfig.getLozinkaKorisnika();
        trazeniPredmet = konfig.getTrazeniPredmet();
        ispravnePorukeDir = null;
        ostalePorukeDir = null;
        super.start();
    }

    @Override
    public void run() {
        //dok je radi==true radi
        while (radi) {
            ukupnoPoruka = 0;
            ispravnihPoruka = 0;
            ostalihPoruka = 0;
            //markiraj pocetak obrade
            startObrade = new Date();
            //obradi email poruke
            obradaMaila();
            try {
                long proteklo = endObrade.getTime() - startObrade.getTime();
                posaljiJMS();
                System.out.println("Šaljem JMS za email / app 2!");
                //"odspavaj" koliko je potrebno da se ispuni kvota intervala
                sleep(interval * 1000 - proteklo);
            } catch (InterruptedException ex) {
                this.interrupt();
            }
        }
        //ako dretva vise ne radi zavrsi je
        this.interrupt();
    }

    @Override
    public void interrupt() {
        radi = false;
        super.interrupt();
    }

    /**
     * Metoda obrade mail poruka i autentifikacije mailova
     */
    private boolean obradaMaila() {
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

        try {
            Properties props = System.getProperties();
            props.put("mail.protocol.port", Integer.valueOf(emailPort));
            props.put("mail.smtp.debug", "true");
            session = Session.getInstance(props, null);
            //getting the session for accessing email
            store = session.getStore("imap");
            //Connection established with IMAP server
            store.connect(emailPosluzitelj, emailKorisnik, emailLozinka);
            // Get a handle on the default folder
            folder = store.getDefaultFolder();
            //Getting the Inbox folder
            // Retrieve the "Inbox" 
            folder = folder.getFolder("inbox");
            //Reading the Email Index in Read / Write Mode
            folder.open(Folder.READ_WRITE);
            //otvara ili kreira foldere ispravnih/neispravnih/ostalih poruka
            ispravnePorukeDir = store.getFolder(konfig.getDirIspravnePoruke());
            if (!ispravnePorukeDir.exists()) {
                ispravnePorukeDir.create(Folder.HOLDS_MESSAGES);
            }
            ispravnePorukeDir.open(Folder.READ_WRITE);
            //ostale dir
            ostalePorukeDir = store.getFolder(konfig.getDirOstalePoruke());
            if (!ostalePorukeDir.exists()) {
                ostalePorukeDir.create(Folder.HOLDS_MESSAGES);
            }
            ostalePorukeDir.open(Folder.READ_WRITE);
            // Retrieve the messages, possible to determine number of messages loaded
            messages = folder.getMessages();
            // Loop over all of the messages
            for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                // Retrieve the next message to be read
                message = messages[messageNumber];
                sveukupnoPoruka++;
                ukupnoPoruka++;
                // Retrieve the message content
                messagecontentObject = message.getContent();
                //rasporedi poruku
                rasporediPoruku(message, folder);
            }
            // Close the folders
            ostalePorukeDir.close(true);
            ispravnePorukeDir.close(true);
            folder.close(true);
            // Close the message store
            store.close();
        } catch (AuthenticationFailedException e) {
            System.out.println(e.toString());
            endObrade = new Date();
            return false;
        } catch (IOException | NumberFormatException | MessagingException e) {
            System.out.println(e.toString());
            endObrade = new Date();
            return false;
        }
        endObrade = new Date();
        return true;
    }

    /**
     * Premjesti poruku u predodredjeni folder prema konfiguraciji ako je poruka
     * ispravna/neispravna
     *
     * @param userChecked String uspjesnosti autentifikacije korisnika
     * @param message Message poruka koju premjestamo
     * @param folder Folder iz kojeg kopiramo poruku
     * @throws MessagingException
     */
    private void rasporediPoruku(Message message, Folder folder) throws MessagingException {
        if (message.getSubject().compareToIgnoreCase(konfig.getTrazeniPredmet()) == 0) {
            ispravnihPoruka++;
            Message[] zaKopiranje = new Message[1];
            zaKopiranje[0] = message;
            folder.copyMessages(zaKopiranje, ispravnePorukeDir);
            message.setFlag(Flags.Flag.DELETED, true);
        } else {
            ostalihPoruka++;
            Message[] zaKopiranje = new Message[1];
            zaKopiranje[0] = message;
            folder.copyMessages(zaKopiranje, ostalePorukeDir);
            message.setFlag(Flags.Flag.DELETED, true);
        }
    }

    public boolean posaljiJMS() {
        try {
            JMSPorukaStruktura message = createJMS();
            saljiJMS(message);
            return true;
        } catch (NamingException | JMSException ex) {
            Logger.getLogger(ObradaPorukaDretva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private JMSPorukaStruktura createJMS() throws JMSException {
        JMSPorukaStruktura tm = new JMSPorukaStruktura(startObrade, endObrade, sveukupnoPoruka, ispravnihPoruka, ostalihPoruka);
        return tm;
    }

    /**
     * Šalje poruku za mail s zadanom strukturom na JMS red NWTiS_hrvoreski_2
     *
     * @param message poruka
     * @throws NamingException
     * @throws JMSException
     */
    private void saljiJMS(JMSPorukaStruktura message) throws NamingException, JMSException {
        Connection connection = null;
        javax.jms.Session session = null;
        try {
            ConnectionFactory cf = (ConnectionFactory) new InitialContext().lookup("jms/NWTiS_QF_hrvoreski_2");
            Queue q = (Queue) new InitialContext().lookup("jms/NWTiS_hrvoreski_2");
            connection = cf.createConnection();
            System.out.println("VEZA " + connection);
            session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(q);
            ObjectMessage o = session.createObjectMessage();
            System.out.println(message.getVrijemePocetka().toString());
            o.setObject(message);
            o.setJMSType(message.getClass().getName());
            messageProducer.send(o);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Nemoguće zatvoriti sesiju!", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
