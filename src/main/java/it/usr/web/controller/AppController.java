/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.controller;

import it.usr.web.service.TitleService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@ApplicationScoped
public class AppController {
    @Inject
    TitleService ts;
    
    public String getTitolo() {
        return ts.getTitle();
    }
}
