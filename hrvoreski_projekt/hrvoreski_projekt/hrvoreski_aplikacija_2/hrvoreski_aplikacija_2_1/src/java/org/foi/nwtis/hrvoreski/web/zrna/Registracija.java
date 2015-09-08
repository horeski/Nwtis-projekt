/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.hrvoreski.ejb.sb.HrvoreskiKorisnikFacade;

/**
 * Managed bean koji služi za registraciju
 * @author Hrvoje
 */
@ManagedBean (eager = true)
@SessionScoped
public class Registracija implements Serializable {
    @EJB
    private HrvoreskiKorisnikFacade hkf;
    
    private String korIme;
    private String lozinka;
    private String ime;
    private int uloga = 0;
    
    
    /**
     * Creates a new instance of Registracija
     */
    public Registracija() {
    }
    
    public String registriraj(){
        hkf.kreirajKorisnika(korIme, lozinka, ime, uloga);
        return "OK";
    }

    public String getKorIme() {
        return korIme;
    }

    public void setKorIme(String korIme) {
        this.korIme = korIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }
    
    
    
}
