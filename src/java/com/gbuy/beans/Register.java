/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.beans;

import com.gbuy.clients.UtilisateurClient;
import com.gbuy.entities.Utilisateur;
import com.google.gson.Gson;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Anas
 */
@ManagedBean(name="register")
@RequestScoped
public class Register {
    
    private Utilisateur user;
    private String password;
    private String dateNaissance;
    private UtilisateurClient client;
    private String message;

    public Register() {
        user = new Utilisateur();
        client = new UtilisateurClient();
        message = "";
    }

    public String adduser(){
         if(!user.getEmail().isEmpty() && !user.getNom().isEmpty() 
                 && !user.getPrenom().isEmpty() && !user.getAdresse().isEmpty()
                 && !user.getVille().isEmpty() && !user.getPays().isEmpty())
         {
             if(user.getPassword().equals(password))
             {
                 Gson gson = new Gson();
                 client.create_JSON(gson.toJson(user));
                 return "index.xhtml";
             }
             else 
             {
                 message = "les mots de passe ne sont pa identique";
             }
         }
         else{
             message = "Il faut remplire tous les champs";
         }    
         return null;
    }
    
    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
