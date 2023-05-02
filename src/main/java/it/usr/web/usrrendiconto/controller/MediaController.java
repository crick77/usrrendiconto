/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller;

import it.usr.web.controller.BaseController;
import it.usr.web.producer.AppLogger;
import it.usr.web.usrrendiconto.domain.Allegato;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class MediaController extends BaseController {
    @Inject
    OrdinativiService os;
    @Resource(lookup = "usrrendiconto/documentFolder")
    String documentFolder;
    @Inject
    @AppLogger
    Logger logger;
    
    public StreamedContent getStream() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // So, browser is requesting the media. Return a real StreamedContent with the media bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            if(id==null) return new DefaultStreamedContent();
            
            Allegato a = os.getAllegatoById(Integer.parseInt(id));
            if(a.getNomefile().toLowerCase().endsWith(".pdf")) {
                String fileName = documentFolder+"/"+a.getNomefileLocale();
                try {
                    byte[] data = Files.readAllBytes(Paths.get(fileName));
                    return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(data))
                                    .contentType("application/pdf").name(a.getNomefile()).build();
                }
                catch(IOException ioe) {            
                    logger.error("Impossibile leggere il file {} a causa di {}.", fileName, ioe);

                    String text = "Impossibile aprire il file. Codice: "+a.getNomefileLocale().split("\\.")[0];
                    return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(getStubDocument(text)))
                                    .contentType("application/pdf").name("Errore").build();                
                }
            }
            else {
                String text = "Il file selezionato non è un PDF. Codice: "+a.getNomefileLocale().split("\\.")[0];
                return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(getStubDocument(text)))
                                .contentType("application/pdf").name("Errore").build();                
            }
        }   
    }
    
    private byte[] getStubDocument(String text) {
        try {        
            ByteArrayOutputStream baos;
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage( page );
                // Create a new font object selecting one of the PDF base fonts
                PDFont font = PDType1Font.HELVETICA_BOLD;
                // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
                try ( // Start a new content stream which will "hold" the to be created content
                        PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    // Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
                    contentStream.beginText();
                    contentStream.setFont(font, 14);
                    contentStream.newLineAtOffset(10, page.getMediaBox().getHeight()-20);
                    contentStream.showText(text);
                    contentStream.endText();
                    // Make sure that the content stream is closed:
                }
                // Save the results and ensure that the document is properly closed:
                baos = new ByteArrayOutputStream();
                document.save(baos);
            }
            
            return baos.toByteArray();
        }
        catch(IOException ioe) {
            return new byte[0];
        }
    }
}
