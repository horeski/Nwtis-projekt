/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.hrvoreski.ejb.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiKorisnik;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiMeteoPortfelj;

/**
 *
 * @author Hrvoje
 */
@Stateless
public class HrvoreskiMeteoPortfeljFacade extends AbstractFacade<HrvoreskiMeteoPortfelj> {

    @EJB
    private HrvoreskiMeteoPortfeljFacade portfeljFacade;

    @PersistenceContext(unitName = "hrvoreski_aplikacija_2_2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrvoreskiMeteoPortfeljFacade() {
        super(HrvoreskiMeteoPortfelj.class);
    }
    /**
     * Dohvati portfelje od korisnika
     * @param korime
     * @return  lista portfelja
     */
    public List<HrvoreskiMeteoPortfelj> dohvatiPortelj(String korime) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<HrvoreskiMeteoPortfelj> root = cq.from(HrvoreskiMeteoPortfelj.class);
        Join<HrvoreskiMeteoPortfelj, HrvoreskiKorisnik> mk = root.join("korime");
        List<Predicate> predikati = new ArrayList<>();
        predikati.add(cb.like(mk.<String>get("korime"), "%" + korime + "%"));
        cq.where(predikati.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }
    /**
     * Dodaj novi portfelj 
     * @param korime
     * @param naziv naziv portfelja
     * @return  true ako je dodan
     */
    public boolean spremiPortfelj(String korime, String naziv) {
        HrvoreskiMeteoPortfelj p = new HrvoreskiMeteoPortfelj();
        HrvoreskiKorisnik k = new HrvoreskiKorisnik();
        k.setKorime(korime);
        p.setNaziv(naziv);
        p.setKorime(k);
        portfeljFacade.create(p);
        return true;
    }
}
