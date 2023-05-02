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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Entrata.findAll", query = "SELECT e FROM Entrata e"),
    @NamedQuery(name = "Entrata.findAllTotali", query = "SELECT DISTINCT e FROM Entrata e LEFT JOIN FETCH e.quietanzaList"),
    @NamedQuery(name = "Entrata.findById", query = "SELECT e FROM Entrata e WHERE e.id = :id"),
    @NamedQuery(name = "Entrata.findByAnnoCompetenza", query = "SELECT e FROM Entrata e WHERE e.annoCompetenza = :annoCompetenza"),
    @NamedQuery(name = "Entrata.findByDisponibilitaL8r", query = "SELECT e FROM Entrata e WHERE e.disponibilitaL8r = :disponibilitaL8r"),
    @NamedQuery(name = "Entrata.findByDescrizione", query = "SELECT e FROM Entrata e WHERE e.descrizione = :descrizione")})
public class Entrata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anno_competenza")
    private int annoCompetenza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "disponibilita_l8r")
    private short disponibilitaL8r;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    private String descrizione;
    @NotNull
    @Version
    private long versione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntrata")
    private List<PrevisioneEntrata> previsioneEntrataList;
    @JoinColumns({
        @JoinColumn(name = "id_codice", referencedColumnName = "id"),
        @JoinColumn(name = "id_codice", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private Codice codice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntrata")
    private List<PrevisioneOrdinativo> previsioneOrdinativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntrata")
    private List<OrdinativoAppoggio> ordinativoAppoggioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntrata")
    private List<Ordinativo> ordinativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntrata")
    private List<Quietanza> quietanzaList;    
    @Transient
    private BigDecimal totale;

    public Entrata() {
    }

    public Entrata(Integer id) {
        this.id = id;
    }

    public Entrata(Integer id, int annoCompetenza, short disponibilitaL8r, String descrizione, long versione) {
        this.id = id;
        this.annoCompetenza = annoCompetenza;
        this.disponibilitaL8r = disponibilitaL8r;
        this.descrizione = descrizione;
        this.versione = versione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnnoCompetenza() {
        return annoCompetenza;
    }

    public void setAnnoCompetenza(int annoCompetenza) {
        this.annoCompetenza = annoCompetenza;
    }

    public short getDisponibilitaL8r() {
        return disponibilitaL8r;
    }

    public void setDisponibilitaL8r(short disponibilitaL8r) {
        this.disponibilitaL8r = disponibilitaL8r;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public long getVersione() {
        return versione;
    }

    public void setVersione(long versione) {
        this.versione = versione;
    }
        
    public List<PrevisioneEntrata> getPrevisioneEntrataList() {
        return previsioneEntrataList;
    }

    public void setPrevisioneEntrataList(List<PrevisioneEntrata> previsioneEntrataList) {
        this.previsioneEntrataList = previsioneEntrataList;
    }

    public Codice getCodice() {
        return codice;
    }

    public void setCodice(Codice codice) {
        this.codice = codice;
    }

    public List<PrevisioneOrdinativo> getPrevisioneOrdinativoList() {
        return previsioneOrdinativoList;
    }

    public void setPrevisioneOrdinativoList(List<PrevisioneOrdinativo> previsioneOrdinativoList) {
        this.previsioneOrdinativoList = previsioneOrdinativoList;
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

    public BigDecimal getTotale() {
        return totale;
    }
              
    public void setTotale(BigDecimal totale) {
        this.totale = totale;
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
        if (!(object instanceof Entrata)) {
            return false;
        }
        Entrata other = (Entrata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.Entrata[ id=" + id + " ]";
    }   
}
