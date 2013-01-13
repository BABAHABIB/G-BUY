/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.BonAchatsClient;
import com.gbuy.clients.CommandeClient;
import com.gbuy.clients.DealsClient;
import com.gbuy.entities.BonAchat;
import com.gbuy.entities.Commande;
import com.gbuy.entities.Deal;
import com.gbuy.entities.Utilisateur;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Anas
 */
@ManagedBean(name = "commander")
@SessionScoped
public class PreparerCommandeBean {

    Deal deal;
    Integer idbc;
    @EJB
    DealsClient dealClient;
    @EJB
    BonAchatsClient baClient;
    @EJB
    CommandeClient cmdClient;

    public String afficherDeatailCommande(Integer id) {
        String reponse = dealClient.find_JSON(String.class, id.toString());
        deal = new Gson().fromJson(reponse, Deal.class);

        ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) eContext.getSession(true);
        if (session.getAttribute("user") != null) {
            return "preparercommande";
        }

        return "loginforCommande";
    }

    public String doCommande() {

        if (deal != null) {
            Commande commande = new Commande();

            ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) eContext.getSession(true);
            Utilisateur usr = (Utilisateur) session.getAttribute("user");

            commande.setIdutilisateur(usr);
            commande.setIddeal(deal);
            commande.setDate(new SimpleDateFormat("dd-MM-YYYY").format(new Date()));

            if (idbc != null) {
                System.out.println("IdBonAchat" + idbc);
                String reponse = baClient.find_JSON(String.class, idbc.toString());
                BonAchat ba = new Gson().fromJson(reponse, BonAchat.class);
                commande.setIdbonAchat(ba);
            }
            cmdClient.create_JSON(new Gson().toJson(commande));
        }
        return "paiement";

    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Integer getIdbc() {
        return idbc;
    }

    public void setIdbc(Integer idbc) {
        this.idbc = idbc;
    }
}
