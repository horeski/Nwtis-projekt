/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiKorisnik;

/**
 *
 * @author Hrvoje
 */
@Stateless
public class HrvoreskiKorisnikFacade extends AbstractFacade<HrvoreskiKorisnik> {

    @PersistenceContext(unitName = "hrvoreski_aplikacija_2_2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrvoreskiKorisnikFacade() {
        super(HrvoreskiKorisnik.class);
    }
    /**
     * Zapisuje novog korisnika u bazu
     * @param korIme
     * @param lozinka
     * @param ime
     * @param uloga
     * @return true za uspješno dodavanje
     */
    public boolean kreirajKorisnika(String korIme, String lozinka, String ime, int uloga) {
        HrvoreskiKorisnik hk = new HrvoreskiKorisnik();
        hk.setIme(ime);
        hk.setKorime(korIme);
        hk.setLozinka(lozinka);
        hk.setUloga(uloga);
        em.persist(hk);
        em.flush();
        return true;
    }
    /**
     * Provjeri da li postoji korisnik s zadanim kor. imenom i lozinikom
     * @param korime
     * @param lozinka
     * @return true/false ovisno o tome postoji li korisnik
     */
    public boolean provjeriKorisnika(String korime, String lozinka) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<HrvoreskiKorisnik> korisnici = cq.from(HrvoreskiKorisnik.class);
        cq.select(korisnici);
        cq.where(cb.and(cb.like(korisnici.<String>get("korime"), korime + "%"), cb.like(korisnici.<String>get("lozinka"), lozinka + "%")));
        int broj = em.createQuery(cq).getResultList().size();
        if (broj == 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Za traženo korime i lozinku vraća korisnika
     * @param korime
     * @param lozinka
     * @return korisnik s tim korimenom/lozinkom
     */
    public List<HrvoreskiKorisnik> vratiKorisnika(String korime, String lozinka) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<HrvoreskiKorisnik> korisnici = cq.from(HrvoreskiKorisnik.class);
        cq.select(korisnici);
        cq.where(cb.like(korisnici.<String>get("korime"), korime + "%"), cb.like(korisnici.<String>get("lozinka"), lozinka + "%"));
        return em.createQuery(cq).getResultList();
    }
    
    public List<HrvoreskiKorisnik> vratiKorisnikID(String korime) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<HrvoreskiKorisnik> korisnici = cq.from(HrvoreskiKorisnik.class);
        cq.select(korisnici);
        cq.where(cb.like(korisnici.<String>get("korime"), korime + "%"));
        return em.createQuery(cq).getResultList();
    }
}
