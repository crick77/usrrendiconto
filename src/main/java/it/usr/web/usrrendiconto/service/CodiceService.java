/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.TipoDocumento;
import it.usr.web.usrrendiconto.domain.TipoRts;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CodiceService {
    @PersistenceContext
    EntityManager em;
    
    public List<Codice> getCodici() {
        return em.createNamedQuery("Codice.findAll", Codice.class).getResultList();
    }
    
    public Codice getCodiceById(int id) {
        return em.find(Codice.class, id);
    }
    
    public Codice getCodiceByCodiceComposto(String codiceComposto) {
        Query q = em.createNamedQuery("Codice.findByCodiceComposto", Codice.class).setParameter("codiceComposto", codiceComposto);
        List<Codice> lQ = q.getResultList();
        return (!lQ.isEmpty()) ? lQ.get(0) : null;
    }
    
    public List<TipoDocumento> getTipiDocumento() {
        return em.createNamedQuery("TipoDocumento.findAll", TipoDocumento.class).getResultList();
    }
    
    public TipoDocumento getTipoDocumentoById(int id) {
        return em.find(TipoDocumento.class, id);
    }
    
     public List<TipoRts> getTipiRts() {
        return em.createNamedQuery("TipoRts.findAll", TipoRts.class).getResultList();
    }
    
    public TipoRts getTipoRtsById(int id) {
        return em.find(TipoRts.class, id);
    }
}
