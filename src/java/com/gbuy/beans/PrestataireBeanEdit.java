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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nihad
 */
@ManagedBean(name = "PrestataireBeanEdit")
@SessionScoped
public class PrestataireBeanEdit {

    private Prestataire prestataire;
    private PrestataireClient prestataireclient;
    private List<Prestataire> listprestataire;
    private JsonParser parser;
    FacesContext context = FacesContext.getCurrentInstance();
    private UIParameter id_prest;

    public PrestataireBeanEdit() {

        prestataire = new Prestataire();
        listprestataire = new ArrayList<Prestataire>();
        prestataireclient = new PrestataireClient();
        parser = new JsonParser();
        remplirelist();


    }

    public void remplirelist() {
        // ----liste des prestataires------------
        String reponseprestataire = prestataireclient.findAll_JSON(String.class);
        System.out.println(reponseprestataire);
        JsonObject jsonPrestataires = (JsonObject) parser.parse(reponseprestataire);
        JsonArray jsonArrayPrestataires = (JsonArray) jsonPrestataires.get("prestataire");
        Gson g = new Gson();
        for (int i = 0; i < jsonArrayPrestataires.size(); i++) {
            Prestataire p = (Prestataire) g.fromJson(jsonArrayPrestataires.get(i), Prestataire.class);
            listprestataire.add(p);
        }

    }

    public String modifier() {

        for (Prestataire p : listprestataire) {

            if (p.getIdprestataire() == Integer.parseInt(id_prest.getValue().toString())) {
                prestataire = p;

                return "edit_prestataire.xhtml";
            }
        }
        return null;
    }

    public String doeditPrestataire() {

        prestataireclient = new PrestataireClient();
        Gson gson = new Gson();
        System.out.println(gson.toJson(prestataire));
        prestataireclient.edit_JSON(gson.toJson(prestataire));
        prestataireclient.close();
        System.out.println("DoModifier");
        return "gestionPrestataire.xhtml";
    }

    //getter - setter
    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public List<Prestataire> getListprestataire() {
        return listprestataire;
    }

    public void setListprestataire(List<Prestataire> listprestataire) {
        this.listprestataire = listprestataire;
    }

    public UIParameter getId_prest() {
        return id_prest;
    }

    public void setId_prest(UIParameter id_prest) {
        this.id_prest = id_prest;
    }
}
