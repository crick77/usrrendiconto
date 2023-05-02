/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Quietanza.findAll", query = "SELECT q FROM Quietanza q"),
    @NamedQuery(name = "Quietanza.findById", query = "SELECT q FROM Quietanza q WHERE q.id = :id"),
    @NamedQuery(name = "Quietanza.findByNumeroPagamento", query = "SELECT q FROM Quietanza q WHERE q.numeroPagamento = :numeroPagamento"),
    @NamedQuery(name = "Quietanza.findByDataPagamento", query = "SELECT q FROM Quietanza q WHERE q.dataPagamento = :dataPagamento"),
    @NamedQuery(name = "Quietanza.findByOrdinante", query = "SELECT q FROM Quietanza q WHERE q.ordinante = :ordinante"),
    @NamedQuery(name = "Quietanza.findByDescrizioneOrdinanza", query = "SELECT q FROM Quietanza q WHERE q.descrizioneOrdinanza = :descrizioneOrdinanza"),
    @NamedQuery(name = "Quietanza.findByImporto", query = "SELECT q FROM Quietanza q WHERE q.importo = :importo"),
    @NamedQuery(name = "Quietanza.findByNote", query = "SELECT q FROM Quietanza q WHERE q.note = :note")})
public class Quietanza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_pagamento")
    private Integer numeroPagamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String ordinante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "descrizione_ordinanza")
    private String descrizioneOrdinanza;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    private BigDecimal importo;
    @Size(max = 255)
    private String note;
    @NotNull
    @Version
    private long versione;
    @JoinColumns({
        @JoinColumn(name = "id_codice", referencedColumnName = "id"),
        @JoinColumn(name = "id_codice", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private Codice codice;
    @JoinColumn(name = "id_entrata", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Entrata idEntrata;
    @JoinColumns({
        @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id"),
        @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private TipoDocumento tipoDocumento;
    @JoinColumns({
        @JoinColumn(name = "id_tipo_rts", referencedColumnName = "id"),
        @JoinColumn(name = "id_tipo_rts", referencedColumnName = "id")})
    @ManyToOne(optional = false)
    private TipoRts tipoRts;

    public Quietanza() {
    }

    public Quietanza(Integer id) {
        this.id = id;
    }

    public Quietanza(Integer id, Date dataPagamento, String ordinante, String descrizioneOrdinanza, BigDecimal importo, long versione) {
        this.id = id;
        this.dataPagamento = dataPagamento;
        this.ordinante = ordinante;
        this.descrizioneOrdinanza = descrizioneOrdinanza;
        this.importo = importo;
        this.versione = versione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroPagamento() {
        return numeroPagamento;
    }

    public void setNumeroPagamento(Integer numeroPagamento) {
        this.numeroPagamento = numeroPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getOrdinante() {
        return ordinante;
    }

    public void setOrdinante(String ordinante) {
        this.ordinante = ordinante;
    }

    public String getDescrizioneOrdinanza() {
        return descrizioneOrdinanza;
    }

    public void setDescrizioneOrdinanza(String descrizioneOrdinanza) {
        this.descrizioneOrdinanza = descrizioneOrdinanza;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getVersione() {
        return versione;
    }

    public void setVersione(long versione) {
        this.versione = versione;
    }
       
    public Codice getCodice() {
        return codice;
    }

    public void setCodice(Codice codice) {
        this.codice = codice;
    }

    public Entrata getIdEntrata() {
        return idEntrata;
    }

    public void setIdEntrata(Entrata idEntrata) {
        this.idEntrata = idEntrata;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public TipoRts getTipoRts() {
        return tipoRts;
    }

    public void setTipoRts(TipoRts tipoRts) {
        this.tipoRts = tipoRts;
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
        if (!(object instanceof Quietanza)) {
            return false;
        }
        Quietanza other = (Quietanza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.Quietanza[ id=" + id + " ]";
    }
    
}
