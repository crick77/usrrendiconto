/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.converter;

import it.usr.web.usrrendiconto.domain.TipoDocumento;
import it.usr.web.usrrendiconto.service.CodiceService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author riccardo.iovenitti
 */
@FacesConverter("tipoDocumentoConverter")
public class TipoDocumentoConverter implements Converter<TipoDocumento> {
    @Inject
    CodiceService cs;
    
    @Override
    public TipoDocumento getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return (string==null) ? null : cs.getTipoDocumentoById(Integer.parseInt(string));
        }
        catch(NumberFormatException e) {
            return null;
        }            
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, TipoDocumento td) {
        return (td==null) ? null : String.valueOf(td.getId());
    }       
}
