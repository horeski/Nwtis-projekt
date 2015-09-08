/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.ejb.eb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "HRVORESKI_METEO_PORTFELJ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrvoreskiMeteoPortfelj.findAll", query = "SELECT h FROM HrvoreskiMeteoPortfelj h"),
    @NamedQuery(name = "HrvoreskiMeteoPortfelj.findByIdportfelj", query = "SELECT h FROM HrvoreskiMeteoPortfelj h WHERE h.idportfelj = :idportfelj"),
    @NamedQuery(name = "HrvoreskiMeteoPortfelj.findByNaziv", query = "SELECT h FROM HrvoreskiMeteoPortfelj h WHERE h.naziv = :naziv")})
public class HrvoreskiMeteoPortfelj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDPORTFELJ")
    private Integer idportfelj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAZIV")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idportfelj")
    private List<HrvoreskiAdresePortfelja> hrvoreskiAdresePortfeljaList;
    @JoinColumn(name = "KORIME", referencedColumnName = "KORIME")
    @ManyToOne
    private HrvoreskiKorisnik korime;

    public HrvoreskiMeteoPortfelj() {
    }

    public HrvoreskiMeteoPortfelj(Integer idportfelj) {
        this.idportfelj = idportfelj;
    }

    public HrvoreskiMeteoPortfelj(Integer idportfelj, String naziv) {
        this.idportfelj = idportfelj;
        this.naziv = naziv;
    }

    public Integer getIdportfelj() {
        return idportfelj;
    }

    public void setIdportfelj(Integer idportfelj) {
        this.idportfelj = idportfelj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<HrvoreskiAdresePortfelja> getHrvoreskiAdresePortfeljaList() {
        return hrvoreskiAdresePortfeljaList;
    }

    public void setHrvoreskiAdresePortfeljaList(List<HrvoreskiAdresePortfelja> hrvoreskiAdresePortfeljaList) {
        this.hrvoreskiAdresePortfeljaList = hrvoreskiAdresePortfeljaList;
    }

    public HrvoreskiKorisnik getKorime() {
        return korime;
    }

    public void setKorime(HrvoreskiKorisnik korime) {
        this.korime = korime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idportfelj != null ? idportfelj.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrvoreskiMeteoPortfelj)) {
            return false;
        }
        HrvoreskiMeteoPortfelj other = (HrvoreskiMeteoPortfelj) object;
        if ((this.idportfelj == null && other.idportfelj != null) || (this.idportfelj != null && !this.idportfelj.equals(other.idportfelj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiMeteoPortfelj[ idportfelj=" + idportfelj + " ]";
    }
    
}
