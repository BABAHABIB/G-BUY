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
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

/**
 *
 * @author yassine
 */

@ManagedBean(name="beanedit")
@SessionScoped
public class DealBeanEdit {
        private Prestataire prestataire;
    private PrestataireClient prestataireclient;
    private List<Prestataire> listprestataire;
    
    FacesContext context = FacesContext.getCurrentInstance();
   
    private DealsClient dealclient;
    private Deal deal;
    private UIParameter id_deal;
    private List<Deal> listDeal;
    private JsonParser parser;   
    private Integer prestataireselected;
    private Categorie categorie;
    private CategorieClient categorieclient;
    private List<Categorie> listcategorie;
    private Integer categorieselected;
       
    
    public DealBeanEdit() {
            
        deal = new Deal();
        prestataire = new Prestataire();
        categorie = new Categorie();
        listDeal = new ArrayList<Deal>();
        listcategorie = new ArrayList<Categorie>();
        listprestataire = new ArrayList<Prestataire>();
        categorieclient = new CategorieClient();
        prestataireclient = new PrestataireClient();
       parser = new JsonParser();
        remplirelist();
      
    }
    public void remplirelist(){
          // ----liste des prestataires------------
        String reponseprestataire = prestataireclient.findAll_JSON(String.class);
        JsonObject jsonPrestataires = (JsonObject) parser.parse(reponseprestataire);
        JsonArray jsonArrayPrestataires = (JsonArray) jsonPrestataires.get("prestataire");
        Gson g = new Gson();
        for (int i = 0; i < jsonArrayPrestataires.size(); i++) {
            Prestataire p = (Prestataire) g.fromJson(jsonArrayPrestataires.get(i), Prestataire.class);
            listprestataire.add(p);
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
    }
    
    

    
       public String doeditDeal() {
 
        dealclient = new DealsClient();
        Gson gson = new Gson();
        System.out.println(gson.toJson(deal));
        dealclient.edit_JSON(gson.toJson(deal));
        dealclient.close();      
        System.out.println("DoModifier");
        return "Administration.xhtml";
}
       
        public String modifier() {
        for (Deal d : listDeal) {
            if (d.getIddeal() == Integer.parseInt(id_deal.getValue().toString())) {
                deal = d;
                return "edit_Deal.xhtml";
            }         
        }
        return null;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public UIParameter getId_deal() {
        return id_deal;
    }

    public void setId_deal(UIParameter id_deal) {
        this.id_deal = id_deal;
    }

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

    public List<Deal> getListDeal() {
        return listDeal;
    }

    public void setListDeal(List<Deal> listDeal) {
        this.listDeal = listDeal;
    }

    public Integer getPrestataireselected() {
        return prestataireselected;
    }

    public void setPrestataireselected(Integer prestataireselected) {
        this.prestataireselected = prestataireselected;
    }

    public List<Categorie> getListcategorie() {
        return listcategorie;
    }

    public void setListcategorie(List<Categorie> listcategorie) {
        this.listcategorie = listcategorie;
    }

    public Integer getCategorieselected() {
        return categorieselected;
    }

    public void setCategorieselected(Integer categorieselected) {
        this.categorieselected = categorieselected;
    }


}
