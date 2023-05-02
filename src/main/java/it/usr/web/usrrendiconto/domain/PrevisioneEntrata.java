/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@Table(name = "previsione_entrata")
@NamedQueries({
    @NamedQuery(name = "PrevisioneEntrata.findAll", query = "SELECT p FROM PrevisioneEntrata p"),
    @NamedQuery(name = "PrevisioneEntrata.findById", query = "SELECT p FROM PrevisioneEntrata p WHERE p.id = :id"),
    @NamedQuery(name = "PrevisioneEntrata.findByDescrizione", query = "SELECT p FROM PrevisioneEntrata p WHERE p.descrizione = :descrizione"),
    @NamedQuery(name = "PrevisioneEntrata.findByImportoPrevisto", query = "SELECT p FROM PrevisioneEntrata p WHERE p.importoPrevisto = :importoPrevisto")})
public class PrevisioneEntrata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String descrizione;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "importo_previsto")
    private BigDecimal importoPrevisto;
    @NotNull
    @Version
    private long versione;
    @JoinColumn(name = "id_entrata", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Entrata idEntrata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrevisioneEntrata")
    private List<PrevisioneQuietanza> previsioneQuietanzaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrevisioneEntrata")
    private List<PrevisioneEntrataOrdinativo> previsioneEntrataOrdinativoList;

    public PrevisioneEntrata() {
    }

    public PrevisioneEntrata(Integer id) {
        this.id = id;
    }

    public PrevisioneEntrata(Integer id, String descrizione, BigDecimal importoPrevisto, long versione) {
        this.id = id;
        this.descrizione = descrizione;
        this.importoPrevisto = importoPrevisto;
        this.versione = versione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public BigDecimal getImportoPrevisto() {
        return importoPrevisto;
    }

    public void setImportoPrevisto(BigDecimal importoPrevisto) {
        this.importoPrevisto = importoPrevisto;
    }

    public long getVersione() {
        return versione;
    }

    public void setVersione(long versione) {
        this.versione = versione;
    }   
    
    public Entrata getIdEntrata() {
        return idEntrata;
    }

    public void setIdEntrata(Entrata idEntrata) {
        this.idEntrata = idEntrata;
    }

    public List<PrevisioneQuietanza> getPrevisioneQuietanzaList() {
        return previsioneQuietanzaList;
    }

    public void setPrevisioneQuietanzaList(List<PrevisioneQuietanza> previsioneQuietanzaList) {
        this.previsioneQuietanzaList = previsioneQuietanzaList;
    }

    public List<PrevisioneEntrataOrdinativo> getPrevisioneEntrataOrdinativoList() {
        return previsioneEntrataOrdinativoList;
    }

    public void setPrevisioneEntrataOrdinativoList(List<PrevisioneEntrataOrdinativo> previsioneEntrataOrdinativoList) {
        this.previsioneEntrataOrdinativoList = previsioneEntrataOrdinativoList;
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
        if (!(object instanceof PrevisioneEntrata)) {
            return false;
        }
        PrevisioneEntrata other = (PrevisioneEntrata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.PrevisioneEntrata[ id=" + id + " ]";
    }
    
}
