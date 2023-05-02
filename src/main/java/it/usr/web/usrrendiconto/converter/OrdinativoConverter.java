/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.converter;

import it.usr.web.usrrendiconto.domain.Ordinativo;
import it.usr.web.usrrendiconto.service.OrdinativiService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author riccardo.iovenitti
 */
@FacesConverter("ordinativoConverter")
public class OrdinativoConverter implements Converter<Ordinativo> {
    @Inject
    OrdinativiService os;
    
    @Override
    public Ordinativo getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return (string==null) ? null : os.getOrdinativoById(Integer.parseInt(string));
        }
        catch(NumberFormatException e) {
            return null;
        }            
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Ordinativo o) {
        return (o==null) ? null : String.valueOf(o.getId());
    }
    
}
