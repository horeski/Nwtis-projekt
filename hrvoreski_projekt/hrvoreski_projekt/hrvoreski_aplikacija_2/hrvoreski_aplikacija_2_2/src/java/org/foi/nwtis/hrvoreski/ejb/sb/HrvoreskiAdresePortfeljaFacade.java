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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiAdresePortfelja;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiAdresePortfelja_;
import org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiMeteoPortfelj;

/**
 *
 * @author Hrvoje
 */
@Stateless
public class HrvoreskiAdresePortfeljaFacade extends AbstractFacade<HrvoreskiAdresePortfelja> {

    @PersistenceContext(unitName = "hrvoreski_aplikacija_2_2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrvoreskiAdresePortfeljaFacade() {
        super(HrvoreskiAdresePortfelja.class);
    }
    /**
     * Dohvaćanje svih adresa iz portfelja
     * @param portfelj birani portfelj
     * @return lista adresa iz traženog portfelja
     */
    public List<HrvoreskiAdresePortfelja> dajAdresePortfelja(HrvoreskiMeteoPortfelj portfelj) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HrvoreskiAdresePortfelja> cq = cb.createQuery(HrvoreskiAdresePortfelja.class);
        Root<HrvoreskiAdresePortfelja> root = cq.from(HrvoreskiAdresePortfelja.class);
        
        Predicate p = cb.equal(root.get(HrvoreskiAdresePortfelja_.idportfelj), portfelj);
        cq.where(p);
        TypedQuery<HrvoreskiAdresePortfelja> q = em.createQuery(cq);
        List<HrvoreskiAdresePortfelja> rezultat = q.getResultList();
        return rezultat;
    }
    /**
     * Za odabrani portfelj obriši id adrese i id portfelja, na taj način obrisat će se adresa iz odabranog portfelja
     * @param brisanje entitetza kojeg se briše
     */
    public void brisiAdresuPortfelja(HrvoreskiAdresePortfelja brisanje) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<HrvoreskiAdresePortfelja> cq = cb.createQuery(HrvoreskiAdresePortfelja.class);
        Root<HrvoreskiAdresePortfelja> root = cq.from(HrvoreskiAdresePortfelja.class);
        
        Predicate p1 = cb.equal(root.get(HrvoreskiAdresePortfelja_.idportfelj), brisanje.getIdportfelj());
        Predicate p2 = cb.equal(root.get(HrvoreskiAdresePortfelja_.idadresa), brisanje.getIdadresa());
        cq.where(cb.and(p1,p2));
        TypedQuery<HrvoreskiAdresePortfelja> q = em.createQuery(cq);
        List<HrvoreskiAdresePortfelja> rezultat = q.getResultList();
        if(!rezultat.isEmpty()){
            HrvoreskiAdresePortfelja brisi = rezultat.get(0);
            this.remove(brisi);         
        }           
    }
}
