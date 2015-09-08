/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.zrna;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.web.slusaci.SlusacAplikacije;

/**
 * Slanje komandi socket serveru tj 1. app bean
 *
 * @author Hrvoje
 */
@ManagedBean
@SessionScoped
public class UpravljanjeKomandama {

    ServletContext cxt;
    private WebKonfiguracija konfig;
    private String input;
    private String output;
    private String posluziteljAdresa;
    private int posluziteljPort;

    /**
     * Creates a new instance of UpravljanjeKomandama
     */
    public UpravljanjeKomandama() {
        cxt = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        setKonfig((WebKonfiguracija) cxt.getAttribute("konfiguracija"));
        posluziteljAdresa = konfig.getAdresaPosluzitelja();
        posluziteljPort = konfig.getPort();      
    }

    public WebKonfiguracija getKonfig() {
        return konfig;
    }

    public void setKonfig(WebKonfiguracija konfig) {
        this.konfig = konfig;
    }
    

    public String getPosluziteljAdresa() {
        return posluziteljAdresa;
    }

    public void setPosluziteljAdresa(String posluziteljAdresa) {
        this.posluziteljAdresa = posluziteljAdresa;
    }

    public int getPosluziteljPort() {
        return posluziteljPort;
    }

    public void setPosluziteljPort(int posluziteljPort) {
        this.posluziteljPort = posluziteljPort;
    }
       
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    
    public String saljiKrozSocket() {
        Socket veza;
        if (input == null || input.length() == 0) {
            output = null;
            return "";
        }
                try {
            veza = new Socket(posluziteljAdresa, posluziteljPort);

            InputStream is = veza.getInputStream();
            OutputStream os = veza.getOutputStream();

            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            osw.write(input);
            osw.flush();
            veza.shutdownOutput();

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
            veza.close();

            output = odgovor.toString();
            return "";
        } catch (ConnectException ex) {
            output = "connection refused";
            return "";
        } catch (IOException ex) {
            Logger.getLogger(UpravljanjeKomandama.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
    }
    
    

}
