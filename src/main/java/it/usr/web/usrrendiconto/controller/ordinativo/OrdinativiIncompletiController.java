/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.ordinativo;

import it.usr.web.controller.BaseController;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Ordinativo;
import it.usr.web.usrrendiconto.domain.Quietanza;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class OrdinativiIncompletiController extends BaseController {
    @Inject
    OrdinativiService os;
    @Inject
    CodiceService cs;    
    List<Ordinativo> ordinativiIncompleti;    
    List<Integer> anni;
    List<Codice> codici;
    List<Ordinativo> ordinativiFiltrate;
    Ordinativo ordinativoSelezionato;
    String[] selectedAnni;
    
    public void initialize() {
        ordinativiIncompleti = os.getOrdinativiIncompleti();
        anni = os.getAnniOrdinativi();
        codici = cs.getCodici();        
    }

    public List<Ordinativo> getOrdinativiIncompleti() {
        return ordinativiIncompleti;
    }        

    public List<Integer> getAnni() {
        return anni;
    }

    public List<Codice> getCodici() {
        return codici;
    }

    public List<Ordinativo> getOrdinativiFiltrate() {
        return ordinativiFiltrate;
    }

    public void setOrdinativiFiltrate(List<Ordinativo> ordinativiFiltrate) {
        this.ordinativiFiltrate = ordinativiFiltrate;
    }
    
    public String[] getSelectedAnni() {
        return selectedAnni;
    }

    public void setSelectedAnni(String[] selectedAnni) {
        this.selectedAnni = selectedAnni;
    }        

    public Ordinativo getOrdinativoSelezionato() {
        return ordinativoSelezionato;
    }

    public void setOrdinativoSelezionato(Ordinativo ordinativoSelezionato) {
        this.ordinativoSelezionato = ordinativoSelezionato;                
        BigDecimal totale = BigDecimal.ZERO;        
        for(Quietanza q : this.ordinativoSelezionato.getIdEntrata().getQuietanzaList()) {
            totale = totale.add(q.getImporto());
        }
        this.ordinativoSelezionato.getIdEntrata().setTotale(totale);        
    }
            
    public boolean anniFilterFuction(Object value, Object filter, Locale locale) {                
        if (selectedAnni == null || selectedAnni.length == 0) return true;
       
        String sValue = String.valueOf(value);
        for(String selectedAnni1 : selectedAnni) {
            if (selectedAnni1.equals(sValue)) return true;            
        }

        return false;
    }
    
    public void eliminaOrdinativo(Ordinativo o) {
        
    }
}
