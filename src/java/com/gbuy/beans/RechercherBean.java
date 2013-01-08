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
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Anas
 */
@ManagedBean(name = "search")
@RequestScoped
public class RechercherBean {

    @EJB
    private DealsClient dealsClient;
    private List<Deal> deals;
    private String tag;
    private final String RECHERCHER = "Trouver un Deal...";

    public RechercherBean() {
        deals = new ArrayList<Deal>();
        tag = RECHERCHER;
    }

    public String search() {
        if (!tag.equals(RECHERCHER) && !tag.isEmpty()) {
            dealsClient = new DealsClient();
            String reponse = dealsClient.search_JSON(String.class, tag);
            dealsClient.close();
            if (reponse != null && !reponse.isEmpty()) {
                System.out.println(reponse);
                Gson g = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject jObject = parser.parse(reponse).getAsJsonObject();
                JsonArray jArray = jObject.getAsJsonArray("deal");
                for (JsonElement elem : jArray) {
                    deals.add(g.fromJson(elem, Deal.class));
                }
                System.out.println("Deals List" + deals.toString());
                return "success";
            }
        }
        return null;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
