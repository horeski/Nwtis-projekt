/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.web.zrna;

import java.io.Serializable;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.hrvoreski.ws.klijenti.Adresa;
import org.foi.nwtis.hrvoreski.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.hrvoreski.ws.klijenti.WeatherData;

/**
 *
 * @author Hrvoje
 */
@ManagedBean
@SessionScoped
public class PregledAdresa implements Serializable {

    @EJB
    private MeteoWSKlijent meteoWSKlijent;
    private Adresa odabranaAdresa;
    private WeatherData meteoPodaci;
    private List<Adresa> adrese;
    private List<Adresa> filteredValue;

    /**
     * Creates a new instance of PregledAdresa
     */
    public PregledAdresa() {
        meteoWSKlijent = new MeteoWSKlijent();
        adrese = meteoWSKlijent.dajSveAdrese();
    }

    public String dajVazeceMeteoPodatke() {
        meteoPodaci = meteoWSKlijent.dajZadnjeMeteoPodatkeZaAdresu(odabranaAdresa.getAdresa());
        return "";
    }

    public Adresa getOdabranaAdresa() {
        return odabranaAdresa;
    }

    public void setOdabranaAdresa(Adresa odabranaAdresa) {
        this.odabranaAdresa = odabranaAdresa;
    }

    public WeatherData getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(WeatherData meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    public List<Adresa> getAdrese() {
        return adrese;
    }

    public void setAdrese(List<Adresa> adrese) {
        this.adrese = adrese;
    }

    public List<Adresa> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Adresa> filteredValue) {
        this.filteredValue = filteredValue;
    }

    public int compare(Object s1, Object s2) {
        Locale hrvatski = new Locale("HR");
        Collator usporedjivac = Collator.getInstance(hrvatski);
        return usporedjivac.compare(s1, s2);
    }

}
