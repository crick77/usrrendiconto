/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.entrata;

import it.usr.web.controller.BaseController;
import it.usr.web.controller.Redirector;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.PrevisioneEntrata;
import it.usr.web.usrrendiconto.domain.Quietanza;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.EntrataService;
import it.usr.web.usrrendiconto.service.PrevisioneEntrataService;
import java.math.BigDecimal;
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
public class PrevisioniEntrataController extends BaseController {
    @Inject
    EntrataService es;
    @Inject
    PrevisioneEntrataService pes;
    @Inject
    CodiceService cs;
    List<PrevisioneEntrata> prevEntrate;
    List<PrevisioneEntrata> prevEntrateFiltrate;
    PrevisioneEntrata previsioneEntrataSelezionata;
    List<Codice> codici;
    
    public void initialize() {
        prevEntrate = pes.getPrevisioniEntrata();
        codici = cs.getCodici();
    }

    public List<PrevisioneEntrata> getPrevEntrate() {
        return prevEntrate;
    }        

    public List<PrevisioneEntrata> getPrevEntrateFiltrate() {
        return prevEntrateFiltrate;
    }

    public void setPrevEntrateFiltrate(List<PrevisioneEntrata> prevEntrateFiltrate) {
        this.prevEntrateFiltrate = prevEntrateFiltrate;
    }

    public PrevisioneEntrata getPrevisioneEntrataSelezionata() {
        return previsioneEntrataSelezionata;
    }

    public void setPrevisioneEntrataSelezionata(PrevisioneEntrata previsioneEntrataSelezionata) {
        this.previsioneEntrataSelezionata = previsioneEntrataSelezionata;
        if(this.previsioneEntrataSelezionata!=null) {
            Entrata e = es.getEntrataById(previsioneEntrataSelezionata.getIdEntrata().getId());
            BigDecimal totale = BigDecimal.ZERO;
            for(Quietanza q : e.getQuietanzaList()) {
                totale = totale.add(q.getImporto());
            }
            this.previsioneEntrataSelezionata.getIdEntrata().setTotale(totale);
        }
    }

    public List<Codice> getCodici() {
        return codici;
    }
                    
    public String modificaPrevisioneEntrata(PrevisioneEntrata pe) {
        return redirect(Redirector.toPath("preventrata").withParam("id", pe.getId()).withRedirect().go());
    }
    
    public void eliminaPrevisioneEntrata(PrevisioneEntrata pe) {
        
    }
}
