/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.DealsClient;
import com.gbuy.entities.Deal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;

/**
 *
 * @author Anas
 */
@ManagedBean(name = "dealTraitementBean")
@SessionScoped
public class DealEJB {

    private UIParameter iddeal;
    private Deal deal;
    @EJB
    private DealsClient dealClient;

    public Deal findDealById(Integer id) {
        String reponse = dealClient.find_JSON(String.class, id.toString());
        if (reponse != null) {
            Gson g = new Gson();
            return g.fromJson(reponse, Deal.class);
        }
        return null;
    }

    public String afficherDetail(Integer id) {
        System.out.println("Details ID :" + id);
        deal = findDealById(id);
        return "detaildeal";
    }

    public Integer nbrAcheteurDuDeal(Integer id) {
        String reponse = dealClient.findCommandeCollectionByIdDeal_JSON(String.class, id.toString());
        if (reponse != null && !reponse.equals("null")) {
            JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();

            try {
                return jObject.getAsJsonArray("commande").size();
            } catch (Exception e) {
                return 1;
            }
        }

        return 0;
    }

    public List<Deal> categorieDeal(Integer idCategorie) {
        List<Deal> list = new ArrayList<Deal>();
        String reponse = dealClient.findByCategorie_JSON(String.class, idCategorie.toString());
        JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();
        try{
                JsonArray jArray = jObject.getAsJsonArray("deal");
                Gson g = new Gson();
                for(JsonElement elem : jArray){
                    Deal d = g.fromJson(elem, Deal.class);
                    list.add(d);
                }
                return list;
                
        }catch(Exception e){
                  
        }
        return null;

    }
    
       public List<Deal> tousDeal() {
        List<Deal> list = new ArrayList<Deal>();
        String reponse = dealClient.findAll_JSON(String.class);
        JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();
        try{
                JsonArray jArray = jObject.getAsJsonArray("deal");
                Gson g = new Gson();
                for(JsonElement elem : jArray){
                    Deal d = g.fromJson(elem, Deal.class);
                    list.add(d);
                }
                return list;
                
        }catch(Exception e){
                  
        }
        return null;

    }

    public int economie(Deal d) {
        if (d != null) {
            return (int) (d.getPrix() * 100 / d.getPrixHabituel());
        }
        return 0;
    }

    public boolean aCadeau() {
        if (deal != null) {
            if (deal.getCadeau() != null && !deal.getCadeau().isEmpty()) {
                if (Integer.parseInt(deal.getCadeau()) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public UIParameter getIddeal() {
        return iddeal;
    }

    public void setIddeal(UIParameter iddeal) {
        this.iddeal = iddeal;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
