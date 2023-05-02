/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.entrata;

import it.usr.web.controller.BaseController;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.Quietanza;
import it.usr.web.usrrendiconto.domain.TipoDocumento;
import it.usr.web.usrrendiconto.domain.TipoRts;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.EntrataService;
import it.usr.web.usrrendiconto.service.QuietanzaService;
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
public class QuietanzaController extends BaseController {
    @Inject
    EntrataService es;
    @Inject
    QuietanzaService qs;
    @Inject
    CodiceService cs;
    private Integer id;
    private Quietanza quietanza;
    private List<Entrata> entrate;
    private List<TipoDocumento> tipiDocumento;
    private List<TipoRts> tipiRts;
    private List<Codice> codici;
    private List<String> ordinanti;
    
    public void initialize() {
        quietanza = (id!=null) ? qs.getQuietanzaById(id) : new Quietanza();
        
        entrate = es.getEntrateTotalizzate();
        codici = cs.getCodici();
        tipiDocumento = cs.getTipiDocumento();
        tipiRts = cs.getTipiRts();
        ordinanti = qs.getOrdinanti();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
 
    public Quietanza getQuietanza() {
        return quietanza;
    }

    public List<Entrata> getEntrate() {
        return entrate;
    }

    public List<TipoDocumento> getTipiDocumento() {
        return tipiDocumento;
    }

    public List<TipoRts> getTipiRts() {
        return tipiRts;
    }

    public List<Codice> getCodici() {
        return codici;
    }
    
    public String getTipoOperazione() {
        return (id!=null) ? "Modifica" : "Nuova";
    }
    
    public List<String> suggerisciOrdinante(String q) {
        final String ql = q.toLowerCase();
        return ordinanti.stream().filter(t -> t.toLowerCase().contains(ql)).collect(Collectors.toList());
    }
    
    public String salva() {
        qs.salva(quietanza);
        
        return redirect("entrate");
    }
    
    public String annulla() {
        return redirect("entrate");
    }
}
