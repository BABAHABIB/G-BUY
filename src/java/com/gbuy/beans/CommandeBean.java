/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.BonAchatClient;
import com.gbuy.clients.DealsClient;
import com.gbuy.clients.UtilisateurClient;
import com.gbuy.entities.BonAchat;
import com.gbuy.entities.Commande;
import com.gbuy.entities.Deal;
import com.gbuy.entities.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Sony
 */
@ManagedBean(name = "CommandeBean")
@RequestScoped
public class CommandeBean {

    private Deal deal;
    private DealsClient dealclient;
    private String idDeal;
    Commande commande;
    Utilisateur user;
    Integer iduser;
    UtilisateurClient userclient;
    BonAchat bonachat;
    Integer idbonachat;
    BonAchatClient bonachatclient;
    private ArrayList<BonAchat> bonachats = new ArrayList<BonAchat>();
    BonAchat bonachatchoisi;
    BonAchatClient bonachatclientchoisi;

    public CommandeBean() {
        deal = new Deal();
        dealclient = new DealsClient();
        idDeal = "1";
        findDealById();
        commande=new Commande();
        iduser=1;
        user=new Utilisateur();
        userclient=new UtilisateurClient();
        bonachat=new BonAchat();
        bonachatclient=new BonAchatClient();
       
        findbonachatsbyiduser();


    }

    public BonAchat getBonachat() {
        return bonachat;
    }

    public void setBonachat(BonAchat bonachat) {
        this.bonachat = bonachat;
    }

    
    
    public void setBonachats(ArrayList<BonAchat> bonachats) {
        this.bonachats = bonachats;
    }

    public ArrayList<BonAchat> getBonachats() {
        return bonachats;
    }
    
    
    private void findDealById(){
        String reponse = dealclient.find_JSON(String.class, idDeal);
        //System.out.println(reponse);
        Gson gson = new Gson();
        deal = gson.fromJson(reponse, Deal.class);
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
    
    private void findbonachatsbyiduser(){
    String reponsebonachat=bonachatclient.findByUserid_JSON(String.class, "1");
    Gson gsonbon = new Gson();
    //System.out.println(reponsebonachat.toString());
    
    JsonParser parser = new JsonParser();
    JsonObject jObject = parser.parse(reponsebonachat).getAsJsonObject();
    JsonArray jArray = jObject.getAsJsonArray("bonAchat");
                
              //  System.out.println(jArray.toString());
                for (JsonElement elem : jArray) {
                    bonachats.add(gsonbon.fromJson(elem, BonAchat.class));
                }
              //  System.out.println("Bon Achat utilisateur List" + bonachats.toString());
                
                
                
    }
    
    public void construirebonachatchoisi(){
    bonachatchoisi=new BonAchat();
    bonachatclientchoisi=new BonAchatClient();
    
     String reponsebonachatchoisi=bonachatclientchoisi.find_JSON(String.class, bonachat.getIdbonAchat().toString());
     Gson gsonchoisi=new Gson();
     bonachatchoisi = gsonchoisi.fromJson(reponsebonachatchoisi, BonAchat.class);
    
    }
    
    public void finduserbyiduser(String iduser){
    
    String reponseuser=userclient.find_JSON(String.class, iduser.toString());
     Gson gsonuser = new Gson();
        user = gsonuser.fromJson(reponseuser, Utilisateur.class);
    }
    
    public void addcommandeconnecte(){
    
        
        commande.setIddeal(deal);
        commande.setIdutilisateur(user);
    
    
    commande.setIdbonAchat(bonachatchoisi);
    DateFormat dateformat=new SimpleDateFormat("dd-MM-YYYY");
    String datee=dateformat.format(new Date());
    commande.setDate(datee);
    
    System.out.println(commande.toString());
    
    
    }
    
    
    public void addcommandenonconnecte(){
    commande.setIddeal(deal);  
    }
    
}
