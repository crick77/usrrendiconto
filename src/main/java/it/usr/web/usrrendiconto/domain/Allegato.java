/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Allegato.findAll", query = "SELECT a FROM Allegato a"),
    @NamedQuery(name = "Allegato.findById", query = "SELECT a FROM Allegato a WHERE a.id = :id"),
    @NamedQuery(name = "Allegato.findByNomefile", query = "SELECT a FROM Allegato a WHERE a.nomefile = :nomefile"),
    @NamedQuery(name = "Allegato.findByDescrizione", query = "SELECT a FROM Allegato a WHERE a.descrizione = :descrizione")})
public class Allegato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    private String nomefile;
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nomefile_locale")
    private String nomefileLocale;
    @Size(max = 32)
    private String descrizione;
    @JoinColumn(name = "id_ordinativo", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Ordinativo idOrdinativo;

    public Allegato() {
    }

    public Allegato(Integer id) {
        this.id = id;
    }

    public Allegato(Integer id, String nomefile, String nomefileLocale) {
        this.id = id;
        this.nomefile = nomefile;
        this.nomefileLocale = nomefileLocale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomefile() {
        return nomefile;
    }

    public void setNomefile(String nomefile) {
        this.nomefile = nomefile;
    }

    public String getNomefileLocale() {
        return nomefileLocale;
    }

    public void setNomefileLocale(String nomefileLocale) {
        this.nomefileLocale = nomefileLocale;
    }
        
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Ordinativo getIdOrdinativo() {
        return idOrdinativo;
    }

    public void setIdOrdinativo(Ordinativo idOrdinativo) {
        this.idOrdinativo = idOrdinativo;
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
        if (!(object instanceof Allegato)) {
            return false;
        }
        Allegato other = (Allegato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.Allegato[ id=" + id + " ]";
    }
    
}
