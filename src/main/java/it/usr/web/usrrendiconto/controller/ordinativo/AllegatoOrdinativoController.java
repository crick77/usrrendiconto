/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller.ordinativo;

import it.usr.web.controller.BaseController;
import it.usr.web.producer.AppLogger;
import it.usr.web.usrrendiconto.domain.Allegato;
import it.usr.web.usrrendiconto.domain.Ordinativo;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ViewScoped
public class AllegatoOrdinativoController extends BaseController {
    @Inject
    OrdinativiService os;
    @Resource(lookup = "usrrendiconto/documentFolder")
    String documentFolder;
    @Inject
    @AppLogger
    Logger logger;
    Integer id;
    Ordinativo ordinativo;
    UploadedFile file;
    List<Allegato> allegati;
    Allegato allegato;
    boolean modalitaAggiunta;
    Integer documentId;
    
    public void initialize() {
        if(id!=null) {
            ordinativo = os.getOrdinativoById(id);
            allegati = os.getAllegatiOrdinativo(id);
            modalitaAggiunta = false;
        }        
        documentId = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Allegato> getAllegati() {
        return allegati;
    }

    public boolean isModalitaAggiunta() {
        return modalitaAggiunta;
    }

    public Ordinativo getOrdinativo() {
        return ordinativo;
    }

    public Allegato getAllegato() {
        return allegato;
    }
        
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }
             
    public void nuovoAllegato() {
        allegato = new Allegato();
        allegato.setIdOrdinativo(ordinativo);
        file = null;
        modalitaAggiunta = true;
    }
        
    public void eliminaAllegato(Allegato a) {
        try {
            os.elimina(a);            
            addMessage(Message.info("Allegato ["+a.getNomefile()+"] eliminato."));
        }
        catch(EJBException ee) {
            logger.error("Impossibile eliminare il file di upload {} a causa di {}.", a.getNomefileLocale(), ee);
            addMessage(Message.fatal("L'allegato non può essere eliminato! Contattare il supporto con codice ["+a.getNomefileLocale().split("\\.")[0]+"]."));
        }
    } 
    
    public void mostraAllegato() {
        PrimeFaces.current().executeScript("PF('documentDialog').show();");
    }
    
    public void salva() {
        if(file==null) {
            addMessage(Message.error("Il file è obbligatorio"));
            return;
        }
        
        String uuid = UUID.randomUUID().toString();
        String localFileName = uuid+".pdf";
        try {
            Files.write(Paths.get(documentFolder+"/"+localFileName), file.getContent());
        }
        catch(IOException ioe) {
            logger.error("Impossibile salvare il file di upload {} a causa di {}.", localFileName, ioe);
            addMessage(Message.fatal("Il documento PDF non può essere salvato! Contattare il supporto con codice ["+uuid+"]."));
            return;
        }
        
        allegato.setNomefile(file.getFileName());
        allegato.setNomefileLocale(localFileName);
        ordinativo.getAllegatoList().add(allegato);
        os.salva(ordinativo);
                
        allegati = os.getAllegatiOrdinativo(id);
        modalitaAggiunta = false;
    }
    
    public void annulla() {
        modalitaAggiunta = false;
    }
    
    public String ordinativi() {
        return redirect("ordinativi");
    }
    
    public void download(Allegato a) {
        String fileName = documentFolder+"/"+a.getNomefileLocale();
        try {
            byte[] data = Files.readAllBytes(Paths.get(fileName));
            Faces.sendFile(data, a.getNomefile(), true);
        }
        catch(IOException ioe) {            
            logger.error("Impossibile leggere il file {} a causa di {}.", fileName, ioe);

            String text = "Impossibile aprire il file. Codice: "+a.getNomefileLocale().split("\\.")[0];
            addMessage(Message.error(text));
        }
    }
}
