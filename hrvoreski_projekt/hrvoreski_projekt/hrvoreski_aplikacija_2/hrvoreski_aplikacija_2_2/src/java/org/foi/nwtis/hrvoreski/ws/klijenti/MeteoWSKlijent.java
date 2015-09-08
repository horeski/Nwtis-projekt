/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.ws.klijenti;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Hrvoje
 */
@Stateless
@LocalBean
public class MeteoWSKlijent {

    public java.util.List<org.foi.nwtis.hrvoreski.ws.klijenti.Adresa> dajSveAdrese() {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveAdrese();
    }

    public java.util.List<org.foi.nwtis.hrvoreski.ws.klijenti.WeatherData> dajMeteoPodatkeZaAdresuUIntervalu(java.lang.String adresa, java.lang.String odDatum, java.lang.String doDatum) {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajMeteoPodatkeZaAdresuUIntervalu(adresa, odDatum, doDatum);
    }

    public java.util.List<org.foi.nwtis.hrvoreski.ws.klijenti.WeatherData> dajPosljednjihNMeteoPodatakaZaAdresu(java.lang.String adresa, int n) {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajPosljednjihNMeteoPodatakaZaAdresu(adresa, n);
    }

    public java.util.List<org.foi.nwtis.hrvoreski.ws.klijenti.Adresa> dajRangListu(int topN) {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajRangListu(topN);
    }

    public java.util.List<org.foi.nwtis.hrvoreski.ws.klijenti.WeatherData> dajSveMeteoPodatkeZaAdresu(java.lang.String adresa) {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaAdresu(adresa);
    }

    public WeatherData dajVazeceMeteoPodatkeZaAdresu(java.lang.String adresa) {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajVazeceMeteoPodatkeZaAdresu(adresa);
    }

    public WeatherData dajZadnjeMeteoPodatkeZaAdresu(java.lang.String adresa) {
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service service = new org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS_Service();
        org.foi.nwtis.hrvoreski.ws.klijenti.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajZadnjeMeteoPodatkeZaAdresu(adresa);
    }

}
