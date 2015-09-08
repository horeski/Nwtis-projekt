/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.ejb.eb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hrvoje
 */
@Entity
@Table(name = "HRVORESKI_KORISNIK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrvoreskiKorisnik.findAll", query = "SELECT h FROM HrvoreskiKorisnik h"),
    @NamedQuery(name = "HrvoreskiKorisnik.findByKorime", query = "SELECT h FROM HrvoreskiKorisnik h WHERE h.korime = :korime"),
    @NamedQuery(name = "HrvoreskiKorisnik.findByLozinka", query = "SELECT h FROM HrvoreskiKorisnik h WHERE h.lozinka = :lozinka"),
    @NamedQuery(name = "HrvoreskiKorisnik.findByUloga", query = "SELECT h FROM HrvoreskiKorisnik h WHERE h.uloga = :uloga"),
    @NamedQuery(name = "HrvoreskiKorisnik.findByIme", query = "SELECT h FROM HrvoreskiKorisnik h WHERE h.ime = :ime")})
public class HrvoreskiKorisnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "KORIME")
    private String korime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "LOZINKA")
    private String lozinka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ULOGA")
    private int uloga;
    @Size(max = 25)
    @Column(name = "IME")
    private String ime;
    @OneToMany(mappedBy = "korime")
    private List<HrvoreskiMeteoPortfelj> hrvoreskiMeteoPortfeljList;

    public HrvoreskiKorisnik() {
    }

    public HrvoreskiKorisnik(String korime) {
        this.korime = korime;
    }

    public HrvoreskiKorisnik(String korime, String lozinka, int uloga) {
        this.korime = korime;
        this.lozinka = lozinka;
        this.uloga = uloga;
    }

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public int getUloga() {
        return uloga;
    }

    public void setUloga(int uloga) {
        this.uloga = uloga;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @XmlTransient
    public List<HrvoreskiMeteoPortfelj> getHrvoreskiMeteoPortfeljList() {
        return hrvoreskiMeteoPortfeljList;
    }

    public void setHrvoreskiMeteoPortfeljList(List<HrvoreskiMeteoPortfelj> hrvoreskiMeteoPortfeljList) {
        this.hrvoreskiMeteoPortfeljList = hrvoreskiMeteoPortfeljList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korime != null ? korime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrvoreskiKorisnik)) {
            return false;
        }
        HrvoreskiKorisnik other = (HrvoreskiKorisnik) object;
        if ((this.korime == null && other.korime != null) || (this.korime != null && !this.korime.equals(other.korime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiKorisnik[ korime=" + korime + " ]";
    }
    
}
