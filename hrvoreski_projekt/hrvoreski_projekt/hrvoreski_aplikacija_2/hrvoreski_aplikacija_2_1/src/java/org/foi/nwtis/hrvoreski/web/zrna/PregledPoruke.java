/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.hrvoreski.web.kontrole.PorukaProsireno;

/**
 * Bean koji prikazuje sadržaj odabrane poruke
 * @author Hrvoje
 */
@ManagedBean
@RequestScoped
public class PregledPoruke {

    private PorukaProsireno poruka;
    /**
     * Konstruktor
     */
    public PregledPoruke() {
    }

    public PorukaProsireno getPoruka() {
        PregledSvihPoruka psp = (PregledSvihPoruka) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pregledSvihPoruka");
        poruka = psp.getPoruka();
        return poruka;
    }

    public void setPoruka(PorukaProsireno poruka) {
        this.poruka = poruka;
    }
    
    
}
