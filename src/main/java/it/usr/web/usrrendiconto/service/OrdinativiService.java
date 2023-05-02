/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.web.usrrendiconto.domain.Allegato;
import it.usr.web.usrrendiconto.domain.Ordinativo;
import it.usr.web.usrrendiconto.domain.OrdinativoAppoggio;
import it.usr.web.usrrendiconto.domain.PrevisioneOrdinativo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJBException;
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
public class OrdinativiService {
    public final static String ORDINATIVO_NONSOGGETTO = "NON SOGGETTO";
    public final static String ORDINATIVO_INATTESA = "IN ATTESA";
    public final static String ORDINATIVO_RIMBORSATO = "RIMBORSATO";
    @PersistenceContext
    EntityManager em;
    @Resource(lookup = "usrrendiconto/documentFolder")
    String documentFolder;
    
    public List<Ordinativo> getOrdinativi() {
        return em.createNamedQuery("Ordinativo.findAll", Ordinativo.class).getResultList();
    }
    
    public List<Ordinativo> getOrdinativiIncompleti() {
        return em.createNamedQuery("Ordinativo.findByRtsCompleto", Ordinativo.class).setParameter("rtsCompleto", 0).getResultList();
    }
    
    public List<Ordinativo> getOrdinativiDaStampare() {
        return em.createNamedQuery("Ordinativo.findByRtsStampato", Ordinativo.class).setParameter("rtsStampato", 0).getResultList();
    }
    
    public List<Integer> getAnniOrdinativi() {
        return em.createQuery("SELECT DISTINCT o.annoCompetenza FROM Ordinativo o ORDER BY o.annoCompetenza DESC", Integer.class).getResultList();
    }
    
    public List<Integer> getAnniPrevisioneOrdinativi() {
        return em.createQuery("SELECT DISTINCT po.annoCompetenza FROM PrevisioneOrdinativo po ORDER BY po.annoCompetenza DESC", Integer.class).getResultList();
    }
    
    public List<PrevisioneOrdinativo> getPrevisioneOrdinativi() {
        return em.createNamedQuery("PrevisioneOrdinativo.findAll", PrevisioneOrdinativo.class).getResultList();
    }
    
    public Ordinativo getOrdinativoById(int id) {
        return em.find(Ordinativo.class, id);
    }
    
    public PrevisioneOrdinativo getPrevisioneOrdinativoById(int id) {
        return em.find(PrevisioneOrdinativo.class, id);
    }
    
    public List<String> getBeneficiari() {
        return em.createQuery("SELECT DISTINCT o.beneficiario FROM Ordinativo o ORDER BY o.beneficiario", String.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public OrdinativoAppoggio salva(OrdinativoAppoggio oa) {
        if(oa.getId()!=null) {
            oa = em.merge(oa);
        }
        else {
            em.persist(oa);
        }        
        
        return oa;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PrevisioneOrdinativo salva(PrevisioneOrdinativo oa) {
        if(oa.getId()!=null) {
            oa = em.merge(oa);
        }
        else {
            em.persist(oa);
        }        
        
        return oa;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void salva(List<OrdinativoAppoggio> lOa) {
        for(OrdinativoAppoggio oa : lOa) {
            if(oa.getId()!=null) {
                em.merge(oa);
            }
            else {
                em.persist(oa);
            }        
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void salva(Ordinativo o) {       
        if(o.getId()!=null) {
            em.merge(o);
        }
        else {
            em.persist(o);
        }                
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void elimina(Allegato a) {
        try {
            em.remove(em.find(Allegato.class, a.getId()));            
            Files.delete(Paths.get(documentFolder+"/"+a.getNomefileLocale()));
        }
        catch(IOException ioe) {
            throw new EJBException(ioe);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void elimina(PrevisioneOrdinativo po) {        
        em.remove(em.find(PrevisioneOrdinativo.class, po.getId()));                    
    }
     
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void elimina(Ordinativo o) {        
        try {
            o = em.find(Ordinativo.class, o.getId());
            List<Allegato> lAll = o.getAllegatoList();
            em.remove(o);
            for(Allegato a : lAll) {
                Files.delete(Paths.get(documentFolder+"/"+a.getNomefileLocale()));
            }
        }
        catch(IOException ioe) {
            throw new EJBException(ioe);
        }
    }
    
    public List<Allegato> getAllegatiOrdinativo(Integer id) {
        Ordinativo o = em.find(Ordinativo.class, id);
        return (o!=null) ? o.getAllegatoList() : new ArrayList<>();
    }
    
    public Allegato getAllegatoById(int id) {
        return em.find(Allegato.class, id);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void salva(Allegato a) {       
        if(a.getId()!=null) {
            em.merge(a);
        }
        else {
            em.persist(a);
        }                
    }
}
