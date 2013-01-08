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
public class JourDealBean {

    Deal dealDuJour;
    @EJB
    DealsClient dealClient;

    public JourDealBean() {
        dealDuJour = findDealDuJour();
    }

    private Deal findDealDuJour() {
        dealClient = new DealsClient();
        String reponse = dealClient.findAll_JSON(String.class);

        JsonParser jParser = new JsonParser();
        JsonObject jObject = jParser.parse(reponse).getAsJsonObject();
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
        dealClient.close();
        Collections.sort(ratings);
        return map.get(ratings.get(ratings.size() - 1));
    }

    public Deal getDealDuJour() {
        return dealDuJour;
    }

    public void setDealDuJour(Deal dealDuJour) {
        this.dealDuJour = dealDuJour;
    }

    @Override
    public String toString() {
        return "JourDealBean{" + "dealDuJour=" + dealDuJour + '}';
    }
}
