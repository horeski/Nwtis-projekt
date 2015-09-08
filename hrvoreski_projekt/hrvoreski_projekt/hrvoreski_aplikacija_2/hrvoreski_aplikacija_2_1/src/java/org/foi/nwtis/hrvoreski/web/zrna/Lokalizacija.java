/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 * Upravlja lokalizacijom servera
 *
 * @author Hrvoje
 *
 */
@ManagedBean
@SessionScoped
public class Lokalizacija implements Serializable {

    private Map<String, Object> jezici;
    private String odabraniJezik = "Hrvatski";
    private Locale odabraniLocale = new Locale("hr");

    /**
     * Creates a new instance of Lokalizacija
     */
    public Lokalizacija() {
        jezici = new HashMap<String, Object>();
        jezici.put("English", Locale.ENGLISH);
        jezici.put("Deutsch", Locale.GERMAN);
        jezici.put("Hrvatski", new Locale("hr"));
        odabraniJezik = "Hrvatski";

    }

    /**
     * Biranje i spremanje odabranog jezika u kontekst aplikacije
     *
     * @return OK u slucaju uspjesnog mijenjanja jezika
     */
    public Object odaberiJezik() {

        for (Map.Entry<String, Object> entry : jezici.entrySet()) {
            if (entry.getValue().toString().equals(odabraniJezik)) {
                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getValue());
                odabraniLocale = (Locale) entry.getValue();
            }
        }
        return "OK";
    }

    public Map<String, Object> getJezici() {
        return jezici;
    }

    public void setJezici(Map<String, Object> jezici) {
        this.jezici = jezici;
    }

    public String getOdabraniJezik() {
        return odabraniJezik;
    }

    public void setOdabraniJezik(String odabraniJezik) {
        this.odabraniJezik = odabraniJezik;
    }

    public Locale getOdabraniLocale() {
        return odabraniLocale;
    }

    public void setOdabraniLocale(Locale odabraniLocale) {
        this.odabraniLocale = odabraniLocale;
    }
    //value change event listener

    /**
     * Change event slušač, kada korisnik klikne na razlicit radio button, jezik
     * se promijeni u kontekstu
     *
     * @param e
     */
    public void changedJezik(ValueChangeEvent e) {

        String newLocaleValue = e.getNewValue().toString();

        //loop country map to compare the locale code
        for (Map.Entry<String, Object> entry : jezici.entrySet()) {

            if (entry.getValue().toString().equals(newLocaleValue)) {
                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getValue());
                odabraniLocale = (Locale) entry.getValue();

            }
        }
    }
}
