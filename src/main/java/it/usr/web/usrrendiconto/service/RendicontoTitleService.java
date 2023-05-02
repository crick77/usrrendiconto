/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.usr.web.usrrendiconto.service;

import it.usr.web.service.TitleService;
import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

/**
 *
 * @author riccardo.iovenitti
 */
@Alternative
@Priority(1)
@Singleton
public class RendicontoTitleService extends TitleService {
    @Override
    public String getTitle() {
        return "USR Rendiconto";
    }    
}
