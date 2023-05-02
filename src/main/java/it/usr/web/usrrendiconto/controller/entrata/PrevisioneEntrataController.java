/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.entrata;

import it.usr.web.controller.BaseController;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.Ordinativo;
import it.usr.web.usrrendiconto.domain.PrevisioneEntrata;
import it.usr.web.usrrendiconto.domain.PrevisioneEntrataOrdinativo;
import it.usr.web.usrrendiconto.service.EntrataService;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import it.usr.web.usrrendiconto.service.PrevisioneEntrataService;
import java.util.ArrayList;
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
public class PrevisioneEntrataController extends BaseController {
    @Inject
    EntrataService es;
    @Inject
    OrdinativiService os;
    @Inject
    PrevisioneEntrataService pes;
    Integer idPrevEntrata;
    PrevisioneEntrata previsioneEntrata;
    List<Entrata> entrate;
    List<Ordinativo> ordinativi;
    List<Ordinativo> ordinativiSelezionati;
    
    public void initialize() {
        if(idPrevEntrata!=null) {
            previsioneEntrata = pes.getPrevisioneEntrataById(idPrevEntrata);
        }
        else {
            previsioneEntrata = new PrevisioneEntrata();
        }
        
        entrate = es.getEntrate();
        ordinativi = os.getOrdinativi();
        ordinativiSelezionati = new ArrayList<>();
    }

    public Integer getIdPrevEntrata() {
        return idPrevEntrata;
    }

    public void setIdPrevEntrata(Integer idPrevEntrata) {
        this.idPrevEntrata = idPrevEntrata;
    }

    public PrevisioneEntrata getPrevisioneEntrata() {
        return previsioneEntrata;
    }

    public List<Entrata> getEntrate() {
        return entrate;
    }

    public List<Ordinativo> getOrdinativi() {
        return ordinativi;
    }

    public List<Ordinativo> getOrdinativiSelezionati() {
        return ordinativiSelezionati;
    }

    public void setOrdinativiSelezionati(List<Ordinativo> ordinativiSelezionati) {
        this.ordinativiSelezionati = ordinativiSelezionati;
    }
                
    public String salva() {
        List<PrevisioneEntrataOrdinativo> peoList = new ArrayList<>(ordinativiSelezionati.size());
        for(Ordinativo o : ordinativiSelezionati) {
            PrevisioneEntrataOrdinativo peo = new PrevisioneEntrataOrdinativo();
            peo.setIdPrevisioneEntrata(previsioneEntrata);
            peo.setIdOrdinativo(o);
            
            peoList.add(peo);
            
            o.setRimborso(OrdinativiService.ORDINATIVO_INATTESA);
        }
        previsioneEntrata.setPrevisioneEntrataOrdinativoList(peoList);
        
        pes.salva(previsioneEntrata);
        
        return redirect("preventrate");
    }
    
    public String annulla() {
        return redirect("preventrate");
    }
}
