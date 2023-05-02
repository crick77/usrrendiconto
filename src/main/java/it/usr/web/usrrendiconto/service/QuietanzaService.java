/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.web.usrrendiconto.domain.Quietanza;
import java.util.List;
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
public class QuietanzaService {
    @PersistenceContext
    EntityManager em;
    
    public List<Quietanza> getQuietanze() {
        return em.createNamedQuery("Quietanza.findAll", Quietanza.class).getResultList();
    }
    
    public Quietanza getQuietanzaById(int id) {
        return em.find(Quietanza.class, id);
    }
    
    public List<String> getOrdinanti() {
        return em.createQuery("SELECT DISTINCT q.ordinante FROM Quietanza q ORDER BY q.ordinante", String.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void salva(Quietanza q) {
        if(q.getId()!=null) {
            em.merge(q);
        } 
        else { 
            em.persist(q);
        }             
    }
           
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void elimina(Quietanza q) {
        em.remove(em.find(Quietanza.class, q.getId()));       
    }
}
  