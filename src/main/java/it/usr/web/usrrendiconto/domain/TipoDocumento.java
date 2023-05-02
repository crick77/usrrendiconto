/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@Table(name = "tipo_documento")
@NamedQueries({
    @NamedQuery(name = "TipoDocumento.findAll", query = "SELECT t FROM TipoDocumento t"),
    @NamedQuery(name = "TipoDocumento.findById", query = "SELECT t FROM TipoDocumento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoDocumento.findByDescrizione", query = "SELECT t FROM TipoDocumento t WHERE t.descrizione = :descrizione")})
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento")
    private List<PrevisioneOrdinativo> previsioneOrdinativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento")
    private List<PrevisioneQuietanza> previsioneQuietanzaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento")
    private List<OrdinativoAppoggio> ordinativoAppoggioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento")
    private List<Ordinativo> ordinativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDocumento")
    private List<Quietanza> quietanzaList;

    public TipoDocumento() {
    }

    public TipoDocumento(Integer id) {
        this.id = id;
    }

    public TipoDocumento(Integer id, String descrizione) {
        this.id = id;
        this.descrizione = descrizione;
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

    public List<PrevisioneOrdinativo> getPrevisioneOrdinativoList() {
        return previsioneOrdinativoList;
    }

    public void setPrevisioneOrdinativoList(List<PrevisioneOrdinativo> previsioneOrdinativoList) {
        this.previsioneOrdinativoList = previsioneOrdinativoList;
    }

    public List<PrevisioneQuietanza> getPrevisioneQuietanzaList() {
        return previsioneQuietanzaList;
    }

    public void setPrevisioneQuietanzaList(List<PrevisioneQuietanza> previsioneQuietanzaList) {
        this.previsioneQuietanzaList = previsioneQuietanzaList;
    }

    public List<OrdinativoAppoggio> getOrdinativoAppoggioList() {
        return ordinativoAppoggioList;
    }

    public void setOrdinativoAppoggioList(List<OrdinativoAppoggio> ordinativoAppoggioList) {
        this.ordinativoAppoggioList = ordinativoAppoggioList;
    }

    public List<Ordinativo> getOrdinativoList() {
        return ordinativoList;
    }

    public void setOrdinativoList(List<Ordinativo> ordinativoList) {
        this.ordinativoList = ordinativoList;
    }

    public List<Quietanza> getQuietanzaList() {
        return quietanzaList;
    }

    public void setQuietanzaList(List<Quietanza> quietanzaList) {
        this.quietanzaList = quietanzaList;
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
        if (!(object instanceof TipoDocumento)) {
            return false;
        }
        TipoDocumento other = (TipoDocumento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.TipoDocumento[ id=" + id + " ]";
    }
    
}
