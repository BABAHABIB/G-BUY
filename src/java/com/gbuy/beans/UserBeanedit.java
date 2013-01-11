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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

/**
 *
 * @author yassine
 */
@ManagedBean(name = "userbeanedit")
@SessionScoped
public class UserBeanedit {
    
    private List<Utilisateur> listuser;
    JsonParser parser = new JsonParser();
    private UIParameter id_user;
    private Utilisateur user;
    private UtilisateurClient userclient;
    private String password1;
    private String password2;
    private List<String> l;
    private String lselected;

    public UserBeanedit() {
        
        userclient = new UtilisateurClient();
        user = new Utilisateur();
        listuser = new ArrayList<Utilisateur>();
        initlistuser();
        l = new ArrayList<String>();
        l.add("Admin");
        l.add("client");
    }
    
    public void initlistuser() {

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
    
    public void doedituser() throws IOException {
        System.out.println(lselected);
        userclient = new UtilisateurClient();
        Gson gson = new Gson();
        if (password1.equals(password2)) {
            user.setType(lselected);
            userclient.edit_JSON(gson.toJson(user));
            userclient.close();
            FacesContext.getCurrentInstance().getExternalContext().redirect("gestionUtilisateur.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Les mots de passe ne sont pas identiques !", "Les mots de passe ne sont pas identiques !"));
           
        }
        
    }
    
    public String modifier() {
        for (Utilisateur u : listuser) {
            if (u.getIdutilisateur() == Integer.parseInt(id_user.getValue().toString())) {
                user = u;
                return "user_edit.xhtml";
            }
        }
        return null;
    }
    
    public List<Utilisateur> getListuser() {
        return listuser;
    }
    
    public void setListuser(List<Utilisateur> listuser) {
        this.listuser = listuser;
    }
    
    public UIParameter getId_user() {
        return id_user;
    }
    
    public void setId_user(UIParameter id_user) {
        this.id_user = id_user;
    }
    
    public Utilisateur getUser() {
        return user;
    }
    
    public void setUser(Utilisateur user) {
        this.user = user;
    }
    
    public String getPassword1() {
        return password1;
    }
    
    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    
    public String getPassword2() {
        return password2;
    }
    
    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public List<String> getL() {
        return l;
    }
    
    public void setL(List<String> l) {
        this.l = l;
    }
    
    public String getLselected() {
        return lselected;
    }
    
    public void setLselected(String lselected) {
        this.lselected = lselected;
    }
}
