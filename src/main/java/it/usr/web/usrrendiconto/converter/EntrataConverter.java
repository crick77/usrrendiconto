/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.converter;

import it.usr.web.usrrendiconto.domain.Entrata;
import it.usr.web.usrrendiconto.service.EntrataService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author riccardo.iovenitti
 */
@FacesConverter("entrataConverter")
public class EntrataConverter implements Converter<Entrata> {
    @Inject
    EntrataService es;
    
    @Override
    public Entrata getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return (string==null) ? null : es.getEntrataById(Integer.parseInt(string));
        }
        catch(NumberFormatException e) {
            return null;
        }            
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Entrata e) {
        return (e==null) ? null : String.valueOf(e.getId());
    }    
}
