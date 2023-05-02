/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller;

import it.usr.web.usrrendiconto.domain.Codice;
import java.lang.reflect.Field;
import java.util.StringJoiner;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ApplicationScoped
public class Formatter {       
    public static String formattaCodice(Codice c) {
        StringJoiner sj = new StringJoiner(".");
        sj.add(c.getCodice());
        for(int i=1;i<=5;i++) {
            try {
                String fName = "c"+((i<10) ? "0" : "")+i;
                Field f = c.getClass().getDeclaredField(fName);
                f.setAccessible(true);
                Object _o = f.get(c);
                if(_o==null) break;
                sj.add(String.valueOf(_o));
            }
            catch(NoSuchFieldException | IllegalAccessException e) {
                break;
            }
        }
        return sj.toString();
    }
}
