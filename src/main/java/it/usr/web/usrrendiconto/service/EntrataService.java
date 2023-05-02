/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.Quietanza;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EntrataService {
    @PersistenceContext
    EntityManager em;
    
    public List<Entrata> getEntrate() {
        return em.createNamedQuery("Entrata.findAll").getResultList();
    }
    
    public List<Entrata> getEntrateTotalizzate() {        
        List<Entrata> lE = em.createNamedQuery("Entrata.findAllTotali").getResultList();
        lE.forEach(e -> {
            BigDecimal totale = BigDecimal.ZERO;        
            for(Quietanza q : e.getQuietanzaList()) {
                totale = totale.add(q.getImporto());
            }
            e.setTotale(totale);                   
        });
        
        return lE;
    }
    
    public Entrata getEntrataById(int id) {
        return em.find(Entrata.class, id);
    }
    
    public List<Integer> getAnniEntrate() {
        return em.createQuery("SELECT DISTINCT e.annoCompetenza FROM Entrata e ORDER BY e.annoCompetenza DESC", Integer.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Entrata salvaEntrata(Entrata e) {
        if(e.getId()==null) {
            em.persist(e);
        }
        else {
            e = em.merge(e);
        }            
        return e;
    }
           
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void eliminaEntrata(Entrata e) {
        e = em.merge(e);
        em.remove(e);
    }
}
