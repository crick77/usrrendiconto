/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@Table(name = "ordinativo_appoggio")
@NamedQueries({
    @NamedQuery(name = "OrdinativoAppoggio.findAll", query = "SELECT o FROM OrdinativoAppoggio o"),
    @NamedQuery(name = "OrdinativoAppoggio.findById", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.id = :id"),
    @NamedQuery(name = "OrdinativoAppoggio.findByAnnoCompetenza", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.annoCompetenza = :annoCompetenza"),
    @NamedQuery(name = "OrdinativoAppoggio.findByNumeroPagamento", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.numeroPagamento = :numeroPagamento"),
    @NamedQuery(name = "OrdinativoAppoggio.findByDataPagamento", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.dataPagamento = :dataPagamento"),
    @NamedQuery(name = "OrdinativoAppoggio.findByBeneficiario", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.beneficiario = :beneficiario"),
    @NamedQuery(name = "OrdinativoAppoggio.findByDescrizioneRts", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.descrizioneRts = :descrizioneRts"),
    @NamedQuery(name = "OrdinativoAppoggio.findByImporto", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.importo = :importo"),
    @NamedQuery(name = "OrdinativoAppoggio.findByNote", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.note = :note"),
    @NamedQuery(name = "OrdinativoAppoggio.findByRtsCompleto", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.rtsCompleto = :rtsCompleto"),
    @NamedQuery(name = "OrdinativoAppoggio.findByRtsStampato", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.rtsStampato = :rtsStampato"),
    @NamedQuery(name = "OrdinativoAppoggio.findByRimborso", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.rimborso = :rimborso"),
    @NamedQuery(name = "OrdinativoAppoggio.findByProprietario", query = "SELECT o FROM OrdinativoAppoggio o WHERE o.proprietario = :proprietario")})
public class OrdinativoAppoggio implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "rts_completo")
    private short rtsCompleto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rts_stampato")
    private short rtsStampato;
    @Size(max = 32)
    private String rimborso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String proprietario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrdinativoAppoggio")
    private List<AllegatoAppoggio> allegatoAppoggioList;
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

    public OrdinativoAppoggio() {
    }

    public OrdinativoAppoggio(Integer id) {
        this.id = id;
    }

    public OrdinativoAppoggio(Integer id, int annoCompetenza, int numeroPagamento, Date dataPagamento, String beneficiario, BigDecimal importo, short rtsCompleto, short rtsStampato, String proprietario) {
        this.id = id;
        this.annoCompetenza = annoCompetenza;
        this.numeroPagamento = numeroPagamento;
        this.dataPagamento = dataPagamento;
        this.beneficiario = beneficiario;
        this.importo = importo;
        this.rtsCompleto = rtsCompleto;
        this.rtsStampato = rtsStampato;
        this.proprietario = proprietario;
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

    public short getRtsCompleto() {
        return rtsCompleto;
    }

    public void setRtsCompleto(short rtsCompleto) {
        this.rtsCompleto = rtsCompleto;
    }

    public short getRtsStampato() {
        return rtsStampato;
    }

    public void setRtsStampato(short rtsStampato) {
        this.rtsStampato = rtsStampato;
    }

    public String getRimborso() {
        return rimborso;
    }

    public void setRimborso(String rimborso) {
        this.rimborso = rimborso;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public List<AllegatoAppoggio> getAllegatoAppoggioList() {
        return allegatoAppoggioList;
    }

    public void setAllegatoAppoggioList(List<AllegatoAppoggio> allegatoAppoggioList) {
        this.allegatoAppoggioList = allegatoAppoggioList;
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
        if (!(object instanceof OrdinativoAppoggio)) {
            return false;
        }
        OrdinativoAppoggio other = (OrdinativoAppoggio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.OrdinativoAppoggio[ id=" + id + " ]";
    }
    
}
