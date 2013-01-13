/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.CommandeClient;
import com.gbuy.clients.DealsClient;
import com.gbuy.entities.Deal;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Anas
 */
@ManagedBean
@RequestScoped
public class BestDealBean {
    @EJB
    private CommandeClient cmdClient;

    @EJB
    DealsClient dealClient ;
    Deal dealDuJour;
    List<Deal> dealInteressant;

    public BestDealBean() {
        dealClient = new DealsClient();
        dealDuJour = findDealDuJour();
        
    }

    private Deal findDealDuJour() {

        String reponse = dealClient.findAll_JSON(String.class);

        JsonParser jParser = new JsonParser();
        JsonObject jObject = jParser.parse(reponse).getAsJsonObject();
        if (jObject.getAsJsonArray("deal").isJsonArray()) {
            JsonArray jArray = jObject.getAsJsonArray("deal");
            Gson gson = new Gson();
            Deal deal;
            List<Integer> ratings = new ArrayList<Integer>();
            Map<Integer, Deal> map = new HashMap<Integer, Deal>();
            for (JsonElement elem : jArray) {
                deal = (gson.fromJson(elem, Deal.class));
                reponse = dealClient.countRatingByIdDeal(deal.getIddeal().toString());
                int rating = Integer.parseInt(reponse);
                ratings.add(rating);
                map.put(rating, deal);

            }
          
            Collections.sort(ratings);

            dealInteressant = new ArrayList<Deal>();
            if (map.size() > 2) {
                dealInteressant.add(map.get(ratings.get(ratings.size() - 2)));
                dealInteressant.add(map.get(ratings.get(ratings.size() - 3)));
            }

            return map.get(ratings.get(ratings.size() - 1));
        }
        return null;

    }

    public List<Deal> getPlusCommander() {
        List<Deal> deals = new ArrayList<Deal>();
      
        String reponse = dealClient.findAll_JSON(String.class);
    

        if (reponse != null && !reponse.equals("null")) {
            JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();
            if (jObject.getAsJsonArray("deal").isJsonArray()) {
                JsonArray jArray = jObject.getAsJsonArray("deal");
                //System.out.println(jArray.toString());
                Gson g = new Gson();

                Map<Integer, Deal> map = new HashMap<Integer, Deal>();
                List<Integer> nbrCmds = new ArrayList<Integer>();
                for (JsonElement c : jArray) {
                    Deal deal = g.fromJson(c, Deal.class);
                    String res = cmdClient.countByDealid(deal.getIddeal().toString());
                    //System.out.println(deal.getIddeal().toString()+" Nbre Commande" +res);

                    if (!map.containsKey(Integer.parseInt(res))) {
                        map.put(Integer.parseInt(res), deal);
                        nbrCmds.add(Integer.parseInt(res));
                    }

                }
               

                Collections.sort(nbrCmds);
                for (int i = 1; i < nbrCmds.size() && i < 5; i++) {
                    deals.add(map.get(nbrCmds.get(nbrCmds.size() - i)));
                }
                //System.out.println("Map Size" + map.toString());
                //System.out.println("CmdSize " + nbrCmds.size());
            }
        }
        //System.out.println("Deal trie" + deals.toString());

        return deals;
    }

    public List<Deal> getNouveauxDeal() {
        List<Deal> deals = new ArrayList<Deal>();
    
        String reponse = dealClient.findAll_JSON(String.class);
 
        // System.out.println(reponse);
        if (reponse != null && !reponse.equals("null")) {
            JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();
            if (jObject.getAsJsonArray("deal").isJsonArray()) {
                JsonArray jArray = jObject.getAsJsonArray("deal");
                Gson g = new Gson();
                for (int i = 1; i < jArray.size() && i < 5; i++) {
                    deals.add(g.fromJson(jArray.get(jArray.size() - i), Deal.class));
                }
            }
            return deals;
        }
        return null;

    }

    public boolean aCadeau() {
        if (dealDuJour.getCadeau() != null && !dealDuJour.getCadeau().isEmpty()) {
            if (Integer.parseInt(dealDuJour.getCadeau()) != 0) {
                return true;
            }
        }
        return false;
    }

    public Deal getDealDuJour() {
        return dealDuJour;
    }

    public void setDealDuJour(Deal dealDuJour) {
        this.dealDuJour = dealDuJour;
    }

    public List<Deal> getDealInteressant() {
        return dealInteressant;
    }

    public void setDealInteressant(List<Deal> dealInteressant) {
        this.dealInteressant = dealInteressant;
    }

    @Override
    public String toString() {
        return "JourDealBean{" + "dealDuJour=" + dealDuJour + '}';
    }
}
