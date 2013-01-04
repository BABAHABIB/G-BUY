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
@ManagedBean(name = "register")
@RequestScoped
public class Register {
    private String password;
    private boolean conditions;
    private Utilisateur user = new Utilisateur();
    private UtilisateurClient client = new UtilisateurClient();


    public String adduser() {
        if (conditions) {
            if (!user.getEmail().isEmpty() && !user.getNom().isEmpty()
                    && !user.getPrenom().isEmpty() && !user.getAdresse().isEmpty()
                    && !user.getVille().isEmpty() && !user.getPays().isEmpty()) {
                if (user.getPassword().equals(password)) {
                    Gson gson = new Gson();
                    client.create_JSON(gson.toJson(user));
                    return "index.xhtml";
                }
            }
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

    public boolean isConditions() {
        return conditions;
    }

    public void setConditions(boolean conditions) {
        this.conditions = conditions;
    }
}