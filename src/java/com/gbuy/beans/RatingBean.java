/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.DealsClient;
import com.gbuy.clients.UtilisateurClient;
import com.gbuy.entities.Deal;
import com.gbuy.entities.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Anas
 */
@ManagedBean
@SessionScoped
public class RatingBean {

    @EJB
    UtilisateurClient utilisateurClient;
    @EJB
    DealsClient dealClient;
    Collection<Deal> ratedDeals;
    UIParameter iddeal;
    Utilisateur usr;
    boolean init = false;

    public RatingBean() {
        ratedDeals = new ArrayList<Deal>();
        if (getUserFromSession() != null) {
            init();
        }
    }

    private Utilisateur getUserFromSession() {
        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) eContext.getSession(false);
        if (session.getAttribute("user") != null) {
            return (Utilisateur) session.getAttribute("user");
        }
        return null;
    }

    private void init() {
        usr = getUserFromSession();
        if (usr != null) {
            try {
                String reponse = utilisateurClient.findRatedDealByUserId_JSON(String.class, usr.getIdutilisateur().toString());
                if (reponse != null && !reponse.equals("null")) {
                    JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();
                    if (jObject.getAsJsonArray("deal").isJsonArray()) {
                        JsonArray jArray = jObject.getAsJsonArray("deal");
                        Gson g = new Gson();
                        for (JsonElement elem : jArray) {
                            Deal d = g.fromJson(elem, Deal.class);
                            ratedDeals.add(d);
                        }
                        System.out.println(ratedDeals);
                    }
                }
            } catch (Exception e) {
            }

            init = true;
        }
    }

    public void rate(ActionEvent e) {
        if (!init) {
            init();
        }

        if (iddeal != null && !iddeal.getValue().toString().isEmpty()) {
            String id = iddeal.getValue().toString();
            usr = getUserFromSession();


            if (usr != null) {
                String reponse = dealClient.find_JSON(String.class, id);

                if (reponse
                        != null) {
                    Gson g = new Gson();
                    Deal deal = g.fromJson(reponse, Deal.class);
                    System.out.println("Deal For Rate :" + deal);

                    if (!ratedDeals.contains(deal)) {
                        ratedDeals.add(deal);

                        reponse = dealClient.findUserCollectionByIdDeal_JSON(String.class, deal.getIddeal().toString());
                        Collection<Utilisateur> cu = new ArrayList<Utilisateur>();
                        if (reponse != null && !reponse.equals("null")) {
                            JsonObject jObject = new JsonParser().parse(reponse).getAsJsonObject();
                            if (jObject.getAsJsonArray("utilisateur").isJsonArray()) {
                                JsonArray jArray = jObject.getAsJsonArray("utilisateur");
                                for (JsonElement elem : jArray) {
                                    Utilisateur u = g.fromJson(elem, Utilisateur.class);
                                    cu.add(u);
                                }
                                System.out.println(cu);
                            }
                        }
                        cu.add(usr);
                        deal.setUtilisateurCollection(cu);
                        dealClient.create_JSON(g.toJson(deal));
                    }

                    System.out.println(" Deal UsrCollection :" + deal.getUtilisateurCollection());
                    System.out.println("Usr Json :" + g.toJson(usr));
                    System.out.println("Rated  :" + ratedDeals);
                }
            } else {
                System.out.println("No User In Session");
            }
        }

    }

    public boolean estRated(Integer id) {
        if (usr != null) {
            for (Deal d : ratedDeals) {
                if (d.getIddeal() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public Collection<Deal> getRatedDeals() {
        return ratedDeals;
    }

    public void setRatedDeals(Collection<Deal> ratedDeals) {
        this.ratedDeals = ratedDeals;
    }

    public UIParameter getIddeal() {
        return iddeal;
    }

    public void setIddeal(UIParameter iddeal) {
        this.iddeal = iddeal;
    }
}
