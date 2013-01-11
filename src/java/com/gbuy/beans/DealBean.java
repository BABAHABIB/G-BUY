/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.CategorieClient;
import com.gbuy.clients.DealsClient;
import com.gbuy.clients.PrestataireClient;
import com.gbuy.entities.Categorie;
import com.gbuy.entities.Deal;
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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;


/**
 *
 * @author yassine
 */
@ManagedBean(name = "dealbean")
@RequestScoped
public class DealBean {

    
    private Deal deal;
    private DealsClient dealclient;
    private List<Deal> listDeal;
    private UIParameter id_deal;
    //-------------------------------
    private Prestataire prestataire;
    private PrestataireClient prestataireclient;
    private List<Prestataire> listprestataire;
    JsonParser parser = new JsonParser();
    FacesContext context = FacesContext.getCurrentInstance();
    private Integer prestataireselected;
    //---------------------------------
    private Categorie categorie;
    private CategorieClient categorieclient;
    private List<Categorie> listcategorie;
    private Integer categorieselected;
    
    


    public DealBean() {
        deal = new Deal();
        listDeal = new ArrayList<Deal>();
        prestataire = new Prestataire();
        prestataireclient = new PrestataireClient();
        listprestataire = new ArrayList<Prestataire>();
        categorie = new Categorie();
        categorieclient = new CategorieClient();
        listcategorie = new ArrayList<Categorie>();
        inite();
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public String addDeal() {

        for (Categorie c : listcategorie) {
            if (c.getIdcategorie() == categorieselected ) {
                deal.setCategorie(c);
                System.out.println("IdCategorie : ");
                System.out.println("IdCategorie : " + c.getIdcategorie());
            }
        }
         for (Prestataire p : listprestataire) {
            if (p.getIdprestataire() == prestataireselected) {
               deal.setPrestataire(p);   
            }         
        }

        Gson gson = new Gson();
        dealclient = new DealsClient();
        System.out.println(gson.toJson(deal));
        dealclient.create_JSON(gson.toJson(deal));
        dealclient.close();
        return "gestionDeal.xhtml";

    }

    private void inite() {

        // ----liste des prestataires------------
        String reponseprestataire = prestataireclient.findAll_JSON(String.class);
        JsonObject jsonPrestataires = (JsonObject) parser.parse(reponseprestataire);
        JsonArray jsonArrayPrestataires = (JsonArray) jsonPrestataires.get("prestataire");
        Gson g = new Gson();
        for (int i = 0; i < jsonArrayPrestataires.size(); i++) {
            Prestataire p = (Prestataire) g.fromJson(jsonArrayPrestataires.get(i), Prestataire.class);
            listprestataire.add(p);
        }

        //-----Liste des Deals--------
        dealclient = new DealsClient();
        String reponseDeal = dealclient.findAll_JSON(String.class);
        dealclient.close();
        JsonObject jsonDeal = (JsonObject) parser.parse(reponseDeal);
        JsonArray jsonArrayDeal = (JsonArray) jsonDeal.get("deal");
        Gson gdeal = new Gson();
        for (int i = 0; i < jsonArrayDeal.size(); i++) {
            Deal d = (Deal) gdeal.fromJson(jsonArrayDeal.get(i), Deal.class);
            listDeal.add(d);
        }

        //--------Lsit des Categories------------
        categorieclient = new CategorieClient();
        String reponseCategorie = categorieclient.findAll_JSON(String.class);
        categorieclient.close();
        JsonObject jsoncategorie = (JsonObject) parser.parse(reponseCategorie);
        JsonArray jsonArraycategorie = (JsonArray) jsoncategorie.get("categorie");
        Gson gcategorie = new Gson();
        for (int i = 0; i < jsonArraycategorie.size(); i++) {
            Categorie c = (Categorie) gcategorie.fromJson(jsonArraycategorie.get(i), Categorie.class);
            listcategorie.add(c);
        }
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public List<Prestataire> getListprestataire() {
        return listprestataire;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Categorie> getListcategorie() {
        return listcategorie;
    }

    public List<Deal> getListDeal() {
        return listDeal;
    }



    public void supprimerDeal() {
        dealclient = new DealsClient();
        dealclient.remove(id_deal.getValue().toString());
        dealclient.close();
        try {
            context.getExternalContext().redirect("gestionDeal.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(DealBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Integer getCategorieselected() {
        return categorieselected;
    }

    public void setCategorieselected(Integer categorieselected) {
        this.categorieselected = categorieselected;
    }

    public Integer getPrestataireselected() {
        return prestataireselected;
    }

    public void setPrestataireselected(Integer prestataireselected) {
        this.prestataireselected = prestataireselected;
    }

    public UIParameter getId_deal() {
        return id_deal;
    }

    public void setId_deal(UIParameter id_deal) {
        this.id_deal = id_deal;
    }
    
    
    
}
