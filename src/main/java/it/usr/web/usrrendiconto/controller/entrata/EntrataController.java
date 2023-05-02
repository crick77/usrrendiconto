/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.entrata;

import it.usr.web.controller.BaseController;
import it.usr.web.controller.Redirector;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.EntrataService;
import java.time.Year;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class EntrataController extends BaseController {
    @Inject
    EntrataService es;
    @Inject
    CodiceService cs;
    List<Codice> codici;
    Entrata entrata;
    boolean l8r;
    Integer idEntrata;
    Redirector entrateView = Redirector.toPath("entrate").withRedirect();
    
    public void initialize() {
        codici = cs.getCodici();
        if(idEntrata==null) {
            entrata = new Entrata();
            entrata.setAnnoCompetenza(Year.now().getValue());
        }
        else {
            entrata = es.getEntrataById(idEntrata);
            l8r = (entrata.getDisponibilitaL8r()==1);
        }
    }

    public Entrata getEntrata() {
        return entrata;
    }

    public void setEntrata(Entrata entrata) {
        this.entrata = entrata;
    }

    public Integer getIdEntrata() {
        return idEntrata;
    }

    public void setIdEntrata(Integer idEntrata) {
        this.idEntrata = idEntrata;
    }

    public List<Codice> getCodici() {
        return codici;
    }

    public boolean isL8r() {
        return l8r;
    }

    public void setL8r(boolean l8r) {
        this.l8r = l8r;
    }
            
    public String salva() {
        entrata.setDisponibilitaL8r(l8r ? (short)1 : 0);
        es.salvaEntrata(entrata);
        return entrateView.go();
    }
    
    public String annulla() {
        return entrateView.go();
    }
}
