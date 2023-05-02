/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.converter;

import it.usr.web.usrrendiconto.domain.TipoRts;
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
@FacesConverter("tipoRtsConverter")
public class TipoRtsConverter implements Converter<TipoRts> {
    @Inject
    CodiceService cs;
    
    @Override
    public TipoRts getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return (string==null) ? null : cs.getTipoRtsById(Integer.parseInt(string));
        }
        catch(NumberFormatException e) {
            return null;
        }            
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, TipoRts t) {
        return (t==null) ? null : String.valueOf(t.getId());
    }       
}

