/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller;

import it.usr.web.controller.AbstractMenuController;
import it.usr.web.controller.MenuItem;
import it.usr.web.controller.SubMenuItem;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class MenuController extends AbstractMenuController {
    private final List<MenuItem> items = new ArrayList<>();;
    
    public MenuController() {
        MenuItem menu = new MenuItem("ENTRATE");
        menu.getSubMenu().add(new SubMenuItem("MACROVOCI", "entrate.xhtml"));
        menu.getSubMenu().add(new SubMenuItem("QUIETANZE", "quietanze.xhtml"));
        menu.getSubMenu().add(new SubMenuItem("PREVISIONI", "preventrate.xhtml"));
        items.add(menu);
        
        menu = new MenuItem("USCITE");
        menu.getSubMenu().add(new SubMenuItem("ORDINATIVI", "ordinativi.xhtml"));        
        menu.getSubMenu().add(new SubMenuItem("INCOMPLETI", "oincompleti.xhtml"));        
        menu.getSubMenu().add(new SubMenuItem("DA STAMPARE", "odastampare.xhtml"));        
        menu.getSubMenu().add(new SubMenuItem("PREVISIONI", "prevordinativi.xhtml"));        
        items.add(menu);
        
        menu = new MenuItem("USCITA");
        menu.getSubMenu().add(new SubMenuItem("ESCI", "quit.xhtml"));
        items.add(menu);
    }
    
    @Override
    public List<MenuItem> getMenuItems() {
        return this.items;
    }        
}
