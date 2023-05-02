/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.web.usrrendiconto.domain.Ordinativo;
import it.usr.web.usrrendiconto.domain.PrevisioneEntrata;
import it.usr.web.usrrendiconto.domain.PrevisioneEntrataOrdinativo;
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
public class PrevisioneEntrataService {
    @PersistenceContext
    EntityManager em;
    
    public List<PrevisioneEntrata> getPrevisioniEntrata() {
        return em.createNamedQuery("PrevisioneEntrata.findAll", PrevisioneEntrata.class).getResultList();
    }
    
    public PrevisioneEntrata getPrevisioneEntrataById(int id) {
        return em.find(PrevisioneEntrata.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PrevisioneEntrata salva(PrevisioneEntrata pe) {
        if(pe.getId()!=null) {
            pe = em.merge(pe);
        }
        else {
            em.persist(pe);
        }
        
        // aggiorna tutti gli ordinativi
        for(PrevisioneEntrataOrdinativo peo : pe.getPrevisioneEntrataOrdinativoList()) {
            em.merge(peo.getIdOrdinativo());
        }
        
        return pe;
    }
}
