package org.foi.nwtis.hrvoreski.web.kontrole;

import java.io.Serializable;
import java.util.Date;

/**
 * Struktura JMS poruke za mail
 *
 * @author Hrvoje
 */
public class JMSPorukaStruktura implements Serializable {

    private Date vrijemePocetka;
    private Date vrijemeZavrsetka;
    private int brojProcitanihPoruka;
    private int brojNwtisPoruka;
    private int brojOstalihPoruka;
    private int brisi;

    public JMSPorukaStruktura(Date vrijemePocetka, Date vrijemeZavrsetka, int brojProcitanihPoruka, int brojNwtisPoruka, int brojOstalihPoruka) {
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeZavrsetka = vrijemeZavrsetka;
        this.brojProcitanihPoruka = brojProcitanihPoruka;
        this.brojNwtisPoruka = brojNwtisPoruka;
        this.brojOstalihPoruka = brojOstalihPoruka;
        brisi = 0;

    }

    public Date getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Date vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Date getVrijemeZavrsetka() {
        return vrijemeZavrsetka;
    }

    public void setVrijemeZavrsetka(Date vrijemeZavrsetka) {
        this.vrijemeZavrsetka = vrijemeZavrsetka;
    }

    public int getBrojProcitanihPoruka() {
        return brojProcitanihPoruka;
    }

    public void setBrojProcitanihPoruka(int brojProcitanihPoruka) {
        this.brojProcitanihPoruka = brojProcitanihPoruka;
    }

    public int getBrojNwtisPoruka() {
        return brojNwtisPoruka;
    }

    public void setBrojNwtisPoruka(int brojNwtisPoruka) {
        this.brojNwtisPoruka = brojNwtisPoruka;
    }

    public int getBrojOstalihPoruka() {
        return brojOstalihPoruka;
    }

    public void setBrojOstalihPoruka(int brojOstalihPoruka) {
        this.brojOstalihPoruka = brojOstalihPoruka;
    }

    public int isBrisi() {
        return brisi;
    }

    public void setBrisi(int brisi) {
        this.brisi = brisi;
    }
}
