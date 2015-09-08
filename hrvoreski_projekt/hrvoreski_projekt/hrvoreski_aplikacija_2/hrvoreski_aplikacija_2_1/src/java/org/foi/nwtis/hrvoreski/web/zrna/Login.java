/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiKorisnik;
import org.foi.nwtis.hrvoreski.ejb.sb.HrvoreskiKorisnikFacade;
import org.foi.nwtis.hrvoreski.web.NeuspjesnaPrijava;
import org.foi.nwtis.hrvoreski.web.kontrole.Korisnik;

/**
 * Managed bean za login
 * @author Hrvoje
 */
@ManagedBean (eager = true)
@SessionScoped
public class Login implements Serializable {
    @EJB
    private HrvoreskiKorisnikFacade hkf;
    
    private String korIme;
    private String lozinka;

    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
    public String login() throws NeuspjesnaPrijava{
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        boolean provjera = hkf.provjeriKorisnika(korIme, lozinka);
        if(korIme == null || korIme.trim().length() == 0 || lozinka == null || lozinka.trim().length() == 0){
            throw new NeuspjesnaPrijava("Nije uneseno korisničko ime ili lozinka");           
        } else if(!provjera){
            throw new NeuspjesnaPrijava("Pogrešno korisničko ime ili lozinka");         
        } else{
            HttpSession sesija = request.getSession();
            List<HrvoreskiKorisnik> korisnik = hkf.vratiKorisnika(korIme, lozinka);
            Korisnik kor;
            kor = new Korisnik(korisnik.get(0).getKorime(),korisnik.get(0).getIme(),korisnik.get(0).getLozinka(),request.getSession().getId(), (int) korisnik.get(0).getUloga());
            sesija.setAttribute("korisnik", kor);
            return "OK";
        }        
    }
   /** 
    public String logout(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession sesija = request.getSession();
        sesija.removeAttribute("korisnik");
        return "OK";
    }
    **/

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
    
    
    
}
