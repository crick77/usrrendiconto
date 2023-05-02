/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@Table(name = "previsione_entrata_ordinativo")
@NamedQueries({
    @NamedQuery(name = "PrevisioneEntrataOrdinativo.findAll", query = "SELECT p FROM PrevisioneEntrataOrdinativo p"),
    @NamedQuery(name = "PrevisioneEntrataOrdinativo.findById", query = "SELECT p FROM PrevisioneEntrataOrdinativo p WHERE p.id = :id")})
public class PrevisioneEntrataOrdinativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @JoinColumn(name = "id_ordinativo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ordinativo idOrdinativo;
    @JoinColumn(name = "id_previsione_entrata", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PrevisioneEntrata idPrevisioneEntrata;

    public PrevisioneEntrataOrdinativo() {
    }

    public PrevisioneEntrataOrdinativo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ordinativo getIdOrdinativo() {
        return idOrdinativo;
    }

    public void setIdOrdinativo(Ordinativo idOrdinativo) {
        this.idOrdinativo = idOrdinativo;
    }

    public PrevisioneEntrata getIdPrevisioneEntrata() {
        return idPrevisioneEntrata;
    }

    public void setIdPrevisioneEntrata(PrevisioneEntrata idPrevisioneEntrata) {
        this.idPrevisioneEntrata = idPrevisioneEntrata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrevisioneEntrataOrdinativo)) {
            return false;
        }
        PrevisioneEntrataOrdinativo other = (PrevisioneEntrataOrdinativo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.PrevisioneEntrataOrdinativo[ id=" + id + " ]";
    }
    
}
