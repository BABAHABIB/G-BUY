/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;


import com.gbuy.clients.DealsClient;
import com.gbuy.entities.Deal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Sony
 */
@ManagedBean(name = "CategorieBean")
@RequestScoped
public class CategorieBean {
    
 private Integer idcategorie;
 private  DealsClient dealsclient;
 private  Deal deal;
 private List<Deal> deallist;
   
   public CategorieBean(){
   dealsclient=new DealsClient();
   deallist=new ArrayList<Deal>();
   deal = new Deal();
   finddealbycategorie();
 
  
   }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public List<Deal> getDeallist() {
        return deallist;
    }

    public void setDeallist(List<Deal> deallist) {
        this.deallist = deallist;
    }
   

    public Integer getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(Integer idcategorie) {
        this.idcategorie = idcategorie;
    }

    public DealsClient getDealsclient() {
        return dealsclient;
    }

    public void setDealsclient(DealsClient dealsclient) {
        this.dealsclient = dealsclient;
    }
    
    
    
    private void finddealbycategorie(){
        
        Gson gson=new Gson();
        String response= dealsclient.findByCategorie_JSON(String.class, "1");
        JsonParser parser = new JsonParser();
        JsonObject jObject = (JsonObject) parser.parse(response);
        JsonArray jsonArrayDeal = (JsonArray) jObject.get("deal");
        Gson gdeal = new Gson();
        for (int i = 0; i < jsonArrayDeal.size(); i++) {
            Deal d = (Deal) gdeal.fromJson(jsonArrayDeal.get(i), Deal.class);
            deallist.add(d);
           
        }
        System.out.println(deallist.toString());
    
        
    
    }
    
    
}
