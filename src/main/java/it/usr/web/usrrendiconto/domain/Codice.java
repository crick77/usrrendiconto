/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author riccardo.iovenitti
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Codice.findAll", query = "SELECT c FROM Codice c"),
    @NamedQuery(name = "Codice.findById", query = "SELECT c FROM Codice c WHERE c.id = :id"),
    @NamedQuery(name = "Codice.findByCodice", query = "SELECT c FROM Codice c WHERE c.codice = :codice"),
    @NamedQuery(name = "Codice.findByC01", query = "SELECT c FROM Codice c WHERE c.c01 = :c01"),
    @NamedQuery(name = "Codice.findByC02", query = "SELECT c FROM Codice c WHERE c.c02 = :c02"),
    @NamedQuery(name = "Codice.findByC03", query = "SELECT c FROM Codice c WHERE c.c03 = :c03"),
    @NamedQuery(name = "Codice.findByC04", query = "SELECT c FROM Codice c WHERE c.c04 = :c04"),
    @NamedQuery(name = "Codice.findByC05", query = "SELECT c FROM Codice c WHERE c.c05 = :c05"),
    @NamedQuery(name = "Codice.findByDescrizione", query = "SELECT c FROM Codice c WHERE c.descrizione = :descrizione"),
    @NamedQuery(name = "Codice.findByNote", query = "SELECT c FROM Codice c WHERE c.note = :note"),
    @NamedQuery(name = "Codice.findByCodiceComposto", query = "SELECT c FROM Codice c WHERE CONCAT(c.codice, c.c01, COALESCE(c.c02, ''), COALESCE(c.c03, ''), COALESCE(c.c04, ''), COALESCE(c.c05, '')) = :codiceComposto"),
})
public class Codice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    private String codice;
    @Size(max = 8)
    private String c01;
    @Size(max = 8)
    private String c02;
    @Size(max = 8)
    private String c03;
    @Size(max = 8)
    private String c04;
    @Size(max = 8)
    private String c05;
    @Size(max = 255)
    private String descrizione;
    @Size(max = 255)
    private String note;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codice")
    private List<Entrata> entrataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codice")
    private List<PrevisioneOrdinativo> previsioneOrdinativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codice")
    private List<PrevisioneQuietanza> previsioneQuietanzaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codice")
    private List<OrdinativoAppoggio> ordinativoAppoggioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codice")
    private List<Ordinativo> ordinativoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codice")
    private List<Quietanza> quietanzaList;

    public Codice() {
    }

    public Codice(Integer id) {
        this.id = id;
    }

    public Codice(Integer id, String codice) {
        this.id = id;
        this.codice = codice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getC01() {
        return c01;
    }

    public void setC01(String c01) {
        this.c01 = c01;
    }

    public String getC02() {
        return c02;
    }

    public void setC02(String c02) {
        this.c02 = c02;
    }

    public String getC03() {
        return c03;
    }

    public void setC03(String c03) {
        this.c03 = c03;
    }

    public String getC04() {
        return c04;
    }

    public void setC04(String c04) {
        this.c04 = c04;
    }

    public String getC05() {
        return c05;
    }

    public void setC05(String c05) {
        this.c05 = c05;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Entrata> getEntrataList() {
        return entrataList;
    }

    public void setEntrataList(List<Entrata> entrataList) {
        this.entrataList = entrataList;
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
        if (!(object instanceof Codice)) {
            return false;
        }
        Codice other = (Codice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.Codice[ id=" + id + " ]";
    }
     
    public String getCodiceComposto() {
        StringJoiner sj = new StringJoiner(".");        
        for(int i=1;i<=5;i++) {
            try {
                String fName = "c"+((i<10) ? "0" : "")+i;
                Field f = this.getClass().getDeclaredField(fName);
                f.setAccessible(true);
                Object _o = f.get(this);
                if(_o==null) break;
                sj.add(String.valueOf(_o));
            }
            catch(NoSuchFieldException | IllegalAccessException e) {
                break;
            }
        }
        String lSJ = sj.toString();
        return getCodice()+(lSJ.length()>0 ? "." : "")+lSJ;
    }
}
