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
import javax.persistence.Table;
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
@Table(name = "previsione_ordinativo")
@NamedQueries({
    @NamedQuery(name = "PrevisioneOrdinativo.findAll", query = "SELECT p FROM PrevisioneOrdinativo p"),
    @NamedQuery(name = "PrevisioneOrdinativo.findById", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.id = :id"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByAnnoCompetenza", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.annoCompetenza = :annoCompetenza"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByNumeroPagamento", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.numeroPagamento = :numeroPagamento"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByDataPagamento", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.dataPagamento = :dataPagamento"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByBeneficiario", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.beneficiario = :beneficiario"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByDescrizioneRts", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.descrizioneRts = :descrizioneRts"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByImporto", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.importo = :importo"),
    @NamedQuery(name = "PrevisioneOrdinativo.findByNote", query = "SELECT p FROM PrevisioneOrdinativo p WHERE p.note = :note")})
public class PrevisioneOrdinativo implements Serializable {

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
    @Column(name = "numero_pagamento")
    private int numeroPagamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String beneficiario;
    @Size(max = 64)
    @Column(name = "descrizione_rts")
    private String descrizioneRts;
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
    @ManyToOne(optional = false)
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

    public PrevisioneOrdinativo() {
    }

    public PrevisioneOrdinativo(Integer id) {
        this.id = id;
    }

    public PrevisioneOrdinativo(Integer id, int annoCompetenza, int numeroPagamento, Date dataPagamento, String beneficiario, BigDecimal importo, long versione) {
        this.id = id;
        this.annoCompetenza = annoCompetenza;
        this.numeroPagamento = numeroPagamento;
        this.dataPagamento = dataPagamento;
        this.beneficiario = beneficiario;
        this.importo = importo;
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

    public int getNumeroPagamento() {
        return numeroPagamento;
    }

    public void setNumeroPagamento(int numeroPagamento) {
        this.numeroPagamento = numeroPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getDescrizioneRts() {
        return descrizioneRts;
    }

    public void setDescrizioneRts(String descrizioneRts) {
        this.descrizioneRts = descrizioneRts;
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
        if (!(object instanceof PrevisioneOrdinativo)) {
            return false;
        }
        PrevisioneOrdinativo other = (PrevisioneOrdinativo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.PrevisioneOrdinativo[ id=" + id + " ]";
    }
    
}
