/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.controller;

import it.usr.web.controller.AbstractAuthController;
import it.usr.web.usrrendiconto.domain.Utente;
import it.usr.web.usrrendiconto.service.UtenteService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riccardo.iovenitti
 */
@Named
@RequestScoped
public class AuthController extends AbstractAuthController {
    @Inject
    UtenteService us;

    @Override
    protected Object getUser(String username) {
        Utente u = us.getUtente(username);
        if(u!=null && u.getAbilitato()==1) return u; else return null;
    }    
}
