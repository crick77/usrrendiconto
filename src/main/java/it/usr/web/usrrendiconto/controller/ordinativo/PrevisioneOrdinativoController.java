/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.ordinativo;

import it.usr.web.controller.BaseController;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.PrevisioneOrdinativo;
import it.usr.web.usrrendiconto.domain.TipoDocumento;
import it.usr.web.usrrendiconto.domain.TipoRts;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.EntrataService;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class PrevisioneOrdinativoController extends BaseController {
    @Inject
    EntrataService es;
    @Inject
    OrdinativiService os;
    @Inject
    CodiceService cs;
    private Integer id;
    private PrevisioneOrdinativo ordinativo;
    private List<Codice> codici;
    private List<TipoRts> tipiRts;
    private List<TipoDocumento> tipiDocumento;
    private List<Entrata> entrate;
    private List<String> beneficiari;
    
    public void initialize() {
        if(id==null) {
            ordinativo = new PrevisioneOrdinativo();
        }
        else {
            ordinativo = os.getPrevisioneOrdinativoById(id);
        }
        
        codici = cs.getCodici();
        tipiRts = cs.getTipiRts();
        entrate = es.getEntrateTotalizzate();
        tipiDocumento = cs.getTipiDocumento();
        beneficiari = os.getBeneficiari();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PrevisioneOrdinativo getOrdinativo() {
        return ordinativo;
    }

    public List<Codice> getCodici() {
        return codici;
    }

    public List<TipoRts> getTipiRts() {
        return tipiRts;
    }

    public List<Entrata> getEntrate() {
        return entrate;
    }

    public List<TipoDocumento> getTipiDocumento() {
        return tipiDocumento;
    }
        
    public List<String> suggerisciBeneficiario(String q) {
        final String ql = q.toLowerCase();
        return beneficiari.stream().filter(t -> t.toLowerCase().contains(ql)).collect(Collectors.toList());
    }
    
    public String salva() {
        os.salva(ordinativo);
        
        return annulla();
    }
    
    public String annulla() {
        return redirect("prevordinativi");
    }
}
