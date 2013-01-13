/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.PrestataireClient;
import com.gbuy.entities.Prestataire;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nihad
 */
@ManagedBean(name = "PrestataireBean")
@RequestScoped
public class PrestataireBean {

    private Prestataire prestataire;
    PrestataireClient client;
    private List<Prestataire> listprestataire;
    JsonParser parser = new JsonParser();
    private UIParameter id_prest;


    public PrestataireBean() {
        prestataire = new Prestataire();
        client = new PrestataireClient();
        listprestataire = new ArrayList<Prestataire>();
       remplireListe();
    

    }
    
  
   public void remplireListe(){
        client = new PrestataireClient();
        String response = client.findAll_JSON(String.class);
        client.close();
        JsonObject json = (JsonObject) parser.parse(response);
        JsonArray jsonArray = (JsonArray) json.get("prestataire");
        Gson g = new Gson();
        for (int i = 0; i < jsonArray.size(); i++) {
            Prestataire p = (Prestataire) g.fromJson(jsonArray.get(i), Prestataire.class);
            listprestataire.add(p);
        }
    }
    
    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public UIParameter getId_prest() {
        return id_prest;
    }

    public void setId_prest(UIParameter id_prest) {
        this.id_prest = id_prest;
    }

    public List<Prestataire> getListprestataire() {
        
        return listprestataire;
    }

    public void setListprestataire(List<Prestataire> listprestataire) {
        this.listprestataire = listprestataire;
    }
    
    
    
    

    // ajouter Prestataire
    public String addPrestataire(ActionEvent event) {

        if (!prestataire.getNom().isEmpty()) {
            String response = null;
            try {
                client = new PrestataireClient();
                response = client.findByNom_JSON(String.class, prestataire.getNom());
                client.close();
            } catch (Exception e) {e.getMessage();
            }
            if (response == null) {
                Gson gson = new Gson();
                client.create_JSON(gson.toJson(prestataire));
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("admin/gestionPrestataire.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ce Nom est déja utilisé !", "Cet Nom est déja utilisé"));
            }
        }

        return null;
    }
    
    
    
    // supprimer un presttaire 
    
     public void supprimerPrestataire() {
        client = new PrestataireClient();
        client.remove(id_prest.getValue().toString());
        client.close();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionPrestataire.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
