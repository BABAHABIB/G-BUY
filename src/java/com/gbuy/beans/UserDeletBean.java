/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.UtilisateurClient;
import com.gbuy.entities.Utilisateur;
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
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

/**
 *
 * @author yassine
 */
@ManagedBean(name="userbean")
@RequestScoped
public class UserDeletBean {

    private UIParameter id_user;
    private Utilisateur user;
    private UtilisateurClient userclient;
    FacesContext context = FacesContext.getCurrentInstance();
    private List<Utilisateur> listuser;
    JsonParser parser = new JsonParser();
    
    public UserDeletBean() {
        
        userclient=new UtilisateurClient();
        user = new Utilisateur();
        listuser=new ArrayList<Utilisateur>();
        initlistuser();
    }
    
        public void supprimeruser() {
        userclient = new UtilisateurClient();
        System.out.println(id_user.getValue().toString());
        userclient.remove(id_user.getValue().toString());
        userclient.close();
        try {
            context.getExternalContext().redirect("gestionUtilisateur.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(DealBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void initlistuser(){
     
        //--------List des Utilisateurs------------
        userclient = new UtilisateurClient();
        String reponseCategorie = userclient.findAll_JSON(String.class);
        userclient.close();
        JsonObject jsonuser = (JsonObject) parser.parse(reponseCategorie);
        JsonArray jsonArrayuser = (JsonArray) jsonuser.get("utilisateur");
        Gson guser = new Gson();
        for (int i = 0; i < jsonArrayuser.size(); i++) {
            Utilisateur u = (Utilisateur) guser.fromJson(jsonArrayuser.get(i), Utilisateur.class);
            listuser.add(u);
        }
     }   

    public List<Utilisateur> getListuser() {
        return listuser;
    }

    public void setListuser(List<Utilisateur> listuser) {
        this.listuser = listuser;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public UIParameter getId_user() {
        return id_user;
    }

    public void setId_user(UIParameter id_user) {
        this.id_user = id_user;
    }


     
     
    
}
