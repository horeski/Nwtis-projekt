/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.hrvoreski.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hrvoje
 */
@Entity
@Table(name = "HRVORESKI_ADRESE_PORTFELJA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrvoreskiAdresePortfelja.findAll", query = "SELECT h FROM HrvoreskiAdresePortfelja h"),
    @NamedQuery(name = "HrvoreskiAdresePortfelja.findByIdadreseportfelja", query = "SELECT h FROM HrvoreskiAdresePortfelja h WHERE h.idadreseportfelja = :idadreseportfelja"),
    @NamedQuery(name = "HrvoreskiAdresePortfelja.findByIdadresa", query = "SELECT h FROM HrvoreskiAdresePortfelja h WHERE h.idadresa = :idadresa")})
public class HrvoreskiAdresePortfelja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDADRESEPORTFELJA")
    private Integer idadreseportfelja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDADRESA")
    private int idadresa;
    @JoinColumn(name = "IDPORTFELJ", referencedColumnName = "IDPORTFELJ")
    @ManyToOne(optional = false)
    private HrvoreskiMeteoPortfelj idportfelj;

    public HrvoreskiAdresePortfelja() {
    }

    public HrvoreskiAdresePortfelja(Integer idadreseportfelja) {
        this.idadreseportfelja = idadreseportfelja;
    }

    public HrvoreskiAdresePortfelja(Integer idadreseportfelja, int idadresa) {
        this.idadreseportfelja = idadreseportfelja;
        this.idadresa = idadresa;
    }

    public Integer getIdadreseportfelja() {
        return idadreseportfelja;
    }

    public void setIdadreseportfelja(Integer idadreseportfelja) {
        this.idadreseportfelja = idadreseportfelja;
    }

    public int getIdadresa() {
        return idadresa;
    }

    public void setIdadresa(int idadresa) {
        this.idadresa = idadresa;
    }

    public HrvoreskiMeteoPortfelj getIdportfelj() {
        return idportfelj;
    }

    public void setIdportfelj(HrvoreskiMeteoPortfelj idportfelj) {
        this.idportfelj = idportfelj;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idadreseportfelja != null ? idadreseportfelja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrvoreskiAdresePortfelja)) {
            return false;
        }
        HrvoreskiAdresePortfelja other = (HrvoreskiAdresePortfelja) object;
        if ((this.idadreseportfelja == null && other.idadreseportfelja != null) || (this.idadreseportfelja != null && !this.idadreseportfelja.equals(other.idadreseportfelja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.hrvoreski.ejb.eb.HrvoreskiAdresePortfelja[ idadreseportfelja=" + idadreseportfelja + " ]";
    }
    
}
