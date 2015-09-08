/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.ws.serveri;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.hrvoreski.konfiguracije.WebKonfiguracija;
import org.foi.nwtis.hrvoreski.rest.klijenti.GoogleMapsKlijent;
import org.foi.nwtis.hrvoreski.rest.klijenti.WeatherBugKlijent;
import org.foi.nwtis.hrvoreski.web.podaci.Adresa;
import org.foi.nwtis.hrvoreski.web.podaci.Location;
import org.foi.nwtis.hrvoreski.web.podaci.WeatherData;
import org.foi.nwtis.hrvoreski.web.slusaci.SlusacAplikacije;

/**
 * Meteo servis s zadanim funkcijama od dobavljanja svih adresa, do unosa nove itd
 * @author Hrvoje
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    /**
     * Web service operation, daj popis svih adresa za koje se prikazuju
     * meterološki podaci
     *
     * @return listu adresa
     */
    @WebMethod(operationName = "dajSveAdrese")
    public List<Adresa> dajSveAdrese() {
        GeoMeteoWSAPI gmapi = new GeoMeteoWSAPI();
        return gmapi.dohvatiSveAdrese();
    }

    /**
     * Web service operation, daje zadnje meteo podatke za traženu adresu
     *
     * @param adresa ulazna adresa
     * @return listu adresa
     */
    @WebMethod(operationName = "dajVazeceMeteoPodatkeZaAdresu")
    public WeatherData dajVazeceMeteoPodatkeZaAdresu(@WebParam(name = "adresa") final String adresa) {
        WebKonfiguracija wk = SlusacAplikacije.getConfig();
        String cKey = wk.getcKey();
        String sKey = wk.getsKey();
        if (adresa != null && adresa.length() > 0) {
            GoogleMapsKlijent gmk = new GoogleMapsKlijent();
            Location loc = gmk.getGeoLocation(adresa);
            WeatherBugKlijent wbk = new WeatherBugKlijent(cKey, sKey);
            WeatherData wd = wbk.getRealTimeWeather(loc.getLatitude(), loc.getLongitude());
            return wd;
        }
        return null;
    }

    /**
     * Web service operation, vraća zadnje poznate meteo podatke iz evidencije
     *
     * @param adresa ulazna adresa
     * @return listu adresa
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaAdresu")
    public WeatherData dajZadnjeMeteoPodatkeZaAdresu(@WebParam(name = "adresa") final String adresa) {
        GeoMeteoWSAPI gmapi = new GeoMeteoWSAPI();
        Integer idAdresa = gmapi.dohvatiIdAdrese(adresa);

        if (idAdresa != null) {
            return gmapi.dohvatiZadnjeMeteoPodatkeZaAdresu(idAdresa);
        }
        return null;
    }

    /**
     * Web service operation, dohvaća sve meteo podatke iz evidencije za traženu
     * adresu
     *
     * @param adresa ulazna adresa
     * @return listu adresa
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaAdresu")
    public List<WeatherData> dajSveMeteoPodatkeZaAdresu(@WebParam(name = "adresa") final String adresa) {
        GeoMeteoWSAPI gmapi = new GeoMeteoWSAPI();
        Integer idAdresa = gmapi.dohvatiIdAdrese(adresa);

        if (idAdresa != null) {
            return gmapi.dohvatiSveMeteoPodatkeZaAdresu(idAdresa);
        }
        return null;
    }

    /**
     * Web service operation Daj rang listu top n adresa za koje je prikupljeno
     * najviše podataka.
     *
     * @param topN koliko adresa se želi prikazati
     * @return lista adresa
     */
    @WebMethod(operationName = "dajRangListu")
    public List<Adresa> dajRangListu(@WebParam(name = "topN") int topN) {
        GeoMeteoWSAPI gmapi = new GeoMeteoWSAPI();
        return gmapi.dajRangListuAdresa(topN);
    }

    /**
     * Web service operation Daje listu posljednih n meteo podataka za adresu
     *
     * @param adresa ulazna adresa
     * @param n broj koliki se želi meteo podataka prikazati
     * @return lista adresa
     */
    @WebMethod(operationName = "dajPosljednjihNMeteoPodatakaZaAdresu")
    public List<WeatherData> dajPosljednjihNMeteoPodatakaZaAdresu(@WebParam(name = "adresa") String adresa, @WebParam(name = "n") int n) {
        GeoMeteoWSAPI gmapi = new GeoMeteoWSAPI();
        return gmapi.dajZadnjihNMeteoPodatakaZaAdresu(adresa, n);
    }

    /**
     * !! java.util.Date se u inputu očekuje kao xml.Gregorian format ??? Web
     * service operation
     *
     * @param adresa
     * @param odDatum
     * @param doDatum
     * @return
     */
    @WebMethod(operationName = "dajMeteoPodatkeZaAdresuUIntervalu")
    public List<WeatherData> dajMeteoPodatkeZaAdresuUIntervalu(@WebParam(name = "adresa") String adresa, @WebParam(name = "odDatum") String odDatum, @WebParam(name = "doDatum") String doDatum) {
        GeoMeteoWSAPI gmapi = new GeoMeteoWSAPI();
        return gmapi.getIntervalMeteoPodatke(adresa, odDatum, doDatum);
    }
}
