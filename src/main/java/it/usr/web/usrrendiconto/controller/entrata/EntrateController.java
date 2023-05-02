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
public class EntrateController extends BaseController {

    @Inject
    EntrataService es;
    @Inject
    CodiceService cs;
    List<Entrata> entrate;
    List<Integer> anni;
    List<Codice> codici;
    List<Entrata> entrateFiltrate;
    String[] selectedAnni;

    public void initialize() {
        entrate = es.getEntrateTotalizzate();
        anni = es.getAnniEntrate();
        codici = cs.getCodici();
    }

    public List<Entrata> getEntrate() {
        return entrate;
    }

    public List<Entrata> getEntrateFiltrate() {
        return entrateFiltrate;
    }

    public void setEntrateFiltrate(List<Entrata> entrateFiltrate) {
        this.entrateFiltrate = entrateFiltrate;
    }

    public List<Integer> getAnni() {
        return anni;
    }

    public List<Codice> getCodici() {
        return codici;
    }

    public String[] getSelectedAnni() {
        return selectedAnni;
    }

    public void setSelectedAnni(String[] selectedAnni) {
        this.selectedAnni = selectedAnni;
    }

    /*public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Entrata e = (Entrata) value;
        return e.getAnnoCompetenza() == filterInt
                || e.getDescrizione().toLowerCase().contains(filterText)
                || e.getCodice().getDescrizione().toLowerCase().contains(filterText)
                || Formatter.formattaCodice(e.getCodice()).toLowerCase().contains(filterText);
    }*/

    public boolean anniFilterFuction(Object value, Object filter, Locale locale) {                
        if (selectedAnni == null || selectedAnni.length == 0) return true;
       
        String sValue = String.valueOf(value);
        for(String selectedAnni1 : selectedAnni) {
            if (selectedAnni1.equals(sValue)) return true;            
        }

        return false;
    }

    public String mostraQuietanze(int idEntrata) {
        return Redirector.toPath("quietanze").withParam("ide", idEntrata).withRedirect().go();
    }

    public String modificaEntrata(int idEntrata) {
        return Redirector.toPath("entrata").withParam("ide", idEntrata).withRedirect().go();
    }

    public void eliminaEntrata(int idEntrata) {
        Entrata e = es.getEntrataById(idEntrata);
        if (e != null) {
            es.eliminaEntrata(e);
            addMessage(Message.info("Entrata eliminata."));
        } else {
            addMessage(Message.warn("Voce già eliminata."));
        }

        initialize();
    }
}
