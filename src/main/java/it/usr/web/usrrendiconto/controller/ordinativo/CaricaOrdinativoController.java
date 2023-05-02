/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.ordinativo;

import it.usr.pdfextract.model.ContoCorrenteBancario;
import it.usr.pdfextract.model.PDFOrdinativo;
import it.usr.pdfextract.model.RiversamentoSuErario;
import it.usr.pdfextract.model.RiversamentoSuTU;
import it.usr.web.controller.BaseController;
import static it.usr.web.controller.BaseController.SAME_VIEW;
import it.usr.web.producer.AppLogger;
import it.usr.web.usrrendiconto.domain.AllegatoAppoggio;
import it.usr.web.usrrendiconto.domain.Codice;
import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.domain.OrdinativoAppoggio;
import it.usr.web.usrrendiconto.domain.TipoDocumento;
import it.usr.web.usrrendiconto.domain.TipoRts;
import it.usr.web.usrrendiconto.service.CodiceService;
import it.usr.web.usrrendiconto.service.EntrataService;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import it.usr.web.usrrendiconto.service.PDFExtractor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import javax.annotation.Resource;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class CaricaOrdinativoController extends BaseController {
    @Inject
    OrdinativiService os;
    @Inject
    PDFExtractor pe;   
    @Inject
    CodiceService cs;
    @Inject
    EntrataService es;
    @Inject
    @AppLogger
    Logger logger;
    @Resource(lookup = "usrrendiconto/documentFolder")
    String documentFolder;
    List<OrdinativoAppoggio> ordinativi;
    List<Codice> codici;
    List<Entrata> entrate;
    List<TipoDocumento> tipiDocumento;
    List<TipoRts> tipiRts;
    Map<String, byte[]> fileData;
    
    public void initialize() {        
        ordinativi = new ArrayList<>();
        codici = cs.getCodici();
        entrate = es.getEntrateTotalizzate();
        tipiDocumento = cs.getTipiDocumento();
        tipiRts = cs.getTipiRts();
        fileData = new HashMap<>();
    }

    public boolean isLoaded() {
        return !ordinativi.isEmpty();
    }

    public List<OrdinativoAppoggio> getOrdinativi() {
        return ordinativi;
    }

    public List<Codice> getCodici() {
        return codici;
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
                       
    public void handleFileUpload(FileUploadEvent event) {                
        try {            
            PDFOrdinativo pdfO = pe.buildOrdinativo(event.getFile().getInputStream());
            
            if(pdfO==null) {
                addMessage(Message.error("Il file ["+event.getFile().getFileName()+"] non è un documento GeoCos!"));
                return;
            }
            
            OrdinativoAppoggio o = new OrdinativoAppoggio();
            o.setNumeroPagamento(pdfO.getNumeroOrdine());
            o.setDataPagamento(pdfO.getDataOrdine());
            o.setAnnoCompetenza(pdfO.getAnnoEmissione());
            o.setImporto(pdfO.getImporto());
            o.setProprietario(getUtente().getUsername());
            AllegatoAppoggio ap = new AllegatoAppoggio();
            ap.setIdOrdinativoAppoggio(o);
            ap.setNomefile(event.getFile().getFileName());
            String fileuuid = UUID.randomUUID().toString()+".pdf";
            ap.setNomefileLocale(fileuuid);
            ap.setDescrizione("PDF Ordinativo");
            o.setAllegatoAppoggioList(new ArrayList<>());
            o.getAllegatoAppoggioList().add(ap);
            fileData.put(fileuuid, event.getFile().getContent());
            
            switch(pdfO.getModalitaEstinzione()) {
                case "56": {
                    ContoCorrenteBancario est = (ContoCorrenteBancario)pdfO.getEstinzione();
                    String ben = !isEmpty(est.getNomeECognomeDelBeneficiario()) ? est.getNomeECognomeDelBeneficiario() : est.getRagioneSocialeDelBeneficiario();
                    o.setBeneficiario(ben);
                    o.setDescrizioneRts(est.getDescrizioneCausale());
                    o.setCodice(getCodiceDescrizione(est.getDescrizioneCausale()));
                    break;
                }         
                case "77": {
                    RiversamentoSuTU est = (RiversamentoSuTU)pdfO.getEstinzione();                    
                    o.setBeneficiario(est.getRagioneSocialeDelBeneficiario());
                    o.setDescrizioneRts(est.getDescrizioneCausale());
                    o.setCodice(getCodiceDescrizione(est.getDescrizioneCausale()));
                    break;
                }         
                case "71": {
                    RiversamentoSuErario est = (RiversamentoSuErario)pdfO.getEstinzione();
                    o.setBeneficiario("ERARIO");
                    o.setDescrizioneRts(est.getDescrizioneCausale());
                    o.setCodice(getCodiceDescrizione(est.getDescrizioneCausale()));
                    break;
                }
            }
                                   
            ordinativi.add(o);
        }
        catch(Exception e) {
            addMessage(Message.error("File "+event.getFile().getFileName()+", errore: "+e));
        }        
    }      
    
    private Codice getCodiceDescrizione(String descrCausale) {
        if(descrCausale!=null && descrCausale.contains("?")) {
            String[] parts = descrCausale.split("\\?");
            return cs.getCodiceByCodiceComposto(parts[1].trim().replace(".", ""));
        }
        
        return null;
    }
    
    public String salva() {
        Map<Integer, List<String>> errors = new HashMap<>();
        for(OrdinativoAppoggio oa : ordinativi) {
            List<String> msg = new ArrayList<>();
            if(oa.getIdEntrata()==null) {
                msg.add("E' necessario indicare l'entrata!");                
            }
            if(oa.getCodice()==null) {
                msg.add("E' necessario indicare il codice!");
            }
            if(oa.getTipoDocumento()==null) {
                msg.add("E' necessario indicare il tipo documento!");
            }
            if(oa.getTipoRts()==null) {
                msg.add("E' necessario indicare il tipo RTS!");
            }
            
            if(!msg.isEmpty()) errors.put(oa.getNumeroPagamento(), msg);
        }
        
        if(errors.isEmpty()) {
            os.salva(ordinativi);
            for(OrdinativoAppoggio oa : ordinativi) {
                AllegatoAppoggio ap = oa.getAllegatoAppoggioList().get(0);
                String fileName = documentFolder+"/"+ap.getNomefileLocale();
                try {
                    Files.write(Paths.get(fileName), fileData.get(ap.getNomefileLocale()));
                }
                catch(IOException ioe) {
                    logger.error("Impossibile salvare il file di upload {} a causa di {}.", fileName, ioe);
                    addMessage(Message.fatal("Il documento PDF non può essere salvato! Contattare il supporto con codice ["+ap.getNomefileLocale()+"]."));
                    return SAME_VIEW;
                }
            }
            return redirect("ordinativi");
        }
        else {
            for(int numPag : errors.keySet()) {
                StringJoiner sj = new StringJoiner(", ", "Pagamento #"+numPag+": ", "");                
                for(String msg : errors.get(numPag)) sj.add(msg);
                
                addMessage(Message.error(sj.toString()));
            }
            
            return SAME_VIEW;
        }
    }
    
    public String annulla() {
        return redirect("ordinativi");
    }
}
