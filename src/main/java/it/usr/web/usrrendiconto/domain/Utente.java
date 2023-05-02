/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.usrrendiconto.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @NamedQuery(name = "Utente.findAll", query = "SELECT u FROM Utente u"),
    @NamedQuery(name = "Utente.findByUsername", query = "SELECT u FROM Utente u WHERE u.username = :username"),
    @NamedQuery(name = "Utente.findByAbilitato", query = "SELECT u FROM Utente u WHERE u.abilitato = :abilitato")})
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Size(min = 1, max = 64)
    @NotNull
    private String username;
    @Basic(optional = false)
    @NotNull
    private short abilitato;

    public Utente() {
    }

    public Utente(String username) {
        this.username = username;
    }

    public Utente(String username, short abilitato) {
        this.username = username;
        this.abilitato = abilitato;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public short getAbilitato() {
        return abilitato;
    }

    public void setAbilitato(short abilitato) {
        this.abilitato = abilitato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utente)) {
            return false;
        }
        Utente other = (Utente) object;
        return !((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username)));
    }

    @Override
    public String toString() {
        return "it.usr.web.usrrendiconto.domain.Utente[ username=" + username + " ]";
    }
    
}
