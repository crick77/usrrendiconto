/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.pdfextract.BeanFiller;
import it.usr.pdfextract.ModalitaEstinzione;
import it.usr.pdfextract.Token;
import it.usr.pdfextract.model.Estinzione;
import it.usr.pdfextract.model.PDFOrdinativo;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)        
public class PDFExtractor {
    public final static Token[] TOKENS = { new Token("Numero Conto / Sezione di Tesoreria:", "/", 2),
                                           new Token("Data Ordine:"), 
                                           new Token("Numero Ordine:"), 
                                           new Token("Anno Emissione:"), 
                                           new Token("Stato Titolo:"), 
                                           new Token("Importo: €"), 
                                           new Token("Importo in lettere:"), 
                                           new Token("Modalità estinzione:", "-", 2), 
                                           new Token("Provenienza fondi:"), 
                                           new Token("Capitolo / Ragioneria / Previsione / Appendice / Articolo:", "/", 5), 
                                           new Token("Codice Amministrazione Autonoma:"),
                                           new Token("Sezione Provenienza Fondi:"),
                                           new Token("Conto CS provenienza Fondi:"),
                                           new Token("Conto TU provenienza Fondi:"),
                                           new Token("Codice ministero FD:"),
                                           new Token("Codice qualifica FD:"),
                                           new Token("Progressivo FD:"),
                                           new Token("Tesoreria FD:"),
                                           new Token("Dettagli provenienza fondi:"),
                                           new Token("CIG:"),
                                           new Token("CUP:"),
                                           new Token("CPV:"),
                                           new Token("Data Esigibilità:"),
                                           new Token("Codice Gestionale: ", "-", 2)
                                        };
    public final static Map<String, Token[]> ESTINZIONI;
    
    public PDFOrdinativo buildOrdinativo(String fileName) throws Exception {
        return buildOrdinativo(new FileInputStream(fileName));
    }
          
    public PDFOrdinativo buildOrdinativo(InputStream file) throws Exception {
        String text;
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
        }
        
        if(text==null || !text.contains("GEOCOS - Ordinativi di contabilità speciale")) {
            return null;
        }
        
        PDFOrdinativo o = new PDFOrdinativo();
        fillBean(text, TOKENS, o);
         
        // Processa estinzione
        Estinzione est = getModalitaEstizione(o.getModalitaEstinzione());        
        fillBean(text, ESTINZIONI.get(o.getModalitaEstinzione()), est);
        
        o.setEstinzione(est);
        return o;
    }
    
    private void fillBean(String text, Token[] tokens, Object bean) throws IllegalAccessException {
        BeanFiller bf = new BeanFiller(bean);
                
        for(int i=0;i<tokens.length;i++) {            
            String tToLow = text.toLowerCase();
            int posStart = tToLow.indexOf(tokens[i].getToken().toLowerCase());
            // trovato?
            if(posStart>=0) {                
                int posEnd = (i<(tokens.length-1)) ? tToLow.indexOf(tokens[i+1].getToken().toLowerCase(), posStart+1) : -1;                

                String value;
                // prima dell'ultimo token?
                if(posEnd>=0) {
                    value = text.substring(posStart+tokens[i].getLength(), posEnd).trim();
                    if(tokens[i].hasSplitChar()) {
                        String[] tokParts = tokens[i].getToken().split(tokens[i].getSplitChar());
                        String[] tokValues = value.split(tokens[i].getSplitChar());

                        for(int k=0;k<tokParts.length;k++) {
                            if(k<tokValues.length) bf.setProperty(Token.sanitize(tokParts[k].trim()), tokValues[k].trim());                                 
                        }
                    }
                    else {
                        bf.setProperty(tokens[i].getSanitizeed(), value);
                    }   
                    
                    posStart = posEnd-1;
                }
                else { // ultimo token
                    value = "";
                    for(int j=posStart+tokens[i].getLength();j<text.length();j++) {
                        if(text.charAt(j)==' ') break;
                        value += text.charAt(j);
                    }                        
                    bf.setProperty(tokens[i].getSanitizeed(), value);
                } 
                
                text = text.substring(posStart+1);
            }
        }
    }
    
    public Estinzione getModalitaEstizione(String modalita) throws Exception {
        Reflections reflections = new Reflections("it.usr.pdfextract.model");
        Set<Class<?>> annotated = reflections.get(Scanners.SubTypes.of(Scanners.TypesAnnotated.with(ModalitaEstinzione.class)).asClass());
        for(Class c : annotated) {
            Annotation ann = c.getAnnotation(ModalitaEstinzione.class);
            Method m = ann.annotationType().getDeclaredMethod("value");
            String v = (String)m.invoke(ann, (Object[])null);
            if(v.equalsIgnoreCase(modalita)) return (Estinzione)c.newInstance();
        }
        
        return null;
    }
    
    static {
        ESTINZIONI = new HashMap<>();
        Token t[] = { new Token("Ragione Sociale del Beneficiario:"),
                      new Token("Conto TU:"), 
                      new Token("Sezione competenza:"), 
                      new Token("Indicatore Fruttifero/Infruttifero:"), 
                      new Token("Cliente Ordinante:"), 
                      new Token("Descrizione Causale:"), 
                      new Token("Firmato digitalmente da:"), 
                      new Token("in data:")
                    };
        ESTINZIONI.put("77", t);
        Token t2[] = { new Token("Ragione Sociale del Beneficiario:"),
                      new Token("Nome e Cognome del Beneficiario:"), 
                      new Token("Tipo Codice Individuale:"), 
                      new Token("BIC:"), 
                      new Token("Codice IBAN:"), 
                      new Token("Cliente ordinante:"), 
                      new Token("Causale valutaria:"), 
                      new Token("Paese di residenza:"), 
                      new Token("Descrizione Causale:"), 
                      new Token("Firmato digitalmente da:"), 
                      new Token("in data:")
                    };
        ESTINZIONI.put("56", t2);   
        Token t3[] = { new Token("Indicatore Competenza / Residui:"),
                      new Token("Capitolo:"), 
                      new Token("Articolo:"), 
                      new Token("Capo:"), 
                      new Token("Versante:"), 
                      new Token("CF Versante:"), 
                      new Token("Codice Versante:"), 
                      new Token("Sezione competenza:"), 
                      new Token("Descrizione Causale:"), 
                      new Token("Firmato digitalmente da:"), 
                      new Token("in data:")
                    };
        ESTINZIONI.put("71", t3);  
    }
}
