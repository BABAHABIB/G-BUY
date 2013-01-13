/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Anas
 */
@ManagedBean
public class Paiment {
    public String payer(ActionEvent e){
         FacesContext context = FacesContext.getCurrentInstance();  
        context.addMessage(null, new FacesMessage("Paiement effectué avec succès", "Merci d'avoir choisie G-BUY"));  
        return null;
    }
}
