/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.entrata;

import it.usr.web.controller.BaseController;
import it.usr.web.controller.Redirector;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.Quietanza;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.EntrataService;
import it.usr.web.usrrendiconto.service.QuietanzaService;
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
public class QuietanzeController extends BaseController {
    @Inject
    QuietanzaService qs;
    @Inject
    EntrataService es;
    @Inject
    CodiceService cs;
    Integer idEntrata;
    Entrata entrata;
    List<Quietanza> quietanze;
    List<Quietanza> quietanzeFiltrate;
    List<Codice> codici;
    
    public void initialize() {
        if(idEntrata!=null) {
            entrata = es.getEntrataById(idEntrata);
            quietanze = entrata.getQuietanzaList();
        }
        else {
            quietanze = qs.getQuietanze();
        }
        
        codici = cs.getCodici();
    }

    public void setIdEntrata(Integer idEntrata) {
        this.idEntrata = idEntrata;
    }

    public Integer getIdEntrata() {
        return idEntrata;
    }
                
    public List<Quietanza> getQuietanze() {
        return quietanze;
    }        

    public List<Quietanza> getQuietanzeFiltrate() {
        return quietanzeFiltrate;
    }

    public void setQuietanzeFiltrate(List<Quietanza> quietanzeFiltrate) {
        this.quietanzeFiltrate = quietanzeFiltrate;
    }

    public List<Codice> getCodici() {
        return codici;
    }
    
    public String modificaQuietanza(Quietanza q) {
        return Redirector.toPath("quietanza").withParam("ide", q.getId()).withRedirect().go();
    }
    
    public void eliminaQuietanza(Quietanza q) {
        qs.elimina(q);
        
        initialize();
    }
}
