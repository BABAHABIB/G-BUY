package com.gbuy.beans;

import com.gbuy.clients.BonAchatsClient;
import com.gbuy.clients.UtilisateurClient;
import com.gbuy.entities.BonAchat;
import com.gbuy.entities.Utilisateur;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Anas
 */
@ManagedBean(name = "utilisateurBean")
@RequestScoped
public class UtilisateursBean {

    @EJB
    private BonAchatsClient bac;
    @EJB
    UtilisateurClient client;
    private String password1;
    private String password2;
    private boolean conditions;
    private String parrainEmail;
    private Utilisateur user;

    public UtilisateursBean() {
        user = new Utilisateur();
        client = new UtilisateurClient();
        bac = new BonAchatsClient();
    }

    public String adduser(ActionEvent event) {

        if (conditions) {
            if (!user.getEmail().isEmpty() && !user.getNom().isEmpty()
                    && !user.getPrenom().isEmpty() && !user.getAdresse().isEmpty()
                    && !user.getVille().isEmpty() && !user.getPays().isEmpty()) {
                if (password1.equals(password2)) {
                    String response = null;
                    try {
                        client = new UtilisateurClient();
                        response = client.findByEmail_JSON(String.class, user.getEmail());
                        client.close();
                    } catch (Exception e) {
                    }
                    if (response == null) {
                        Gson gson = new Gson();
                        user.setPassword(password1);
                        client.create_JSON(gson.toJson(user));
                        if (!parrainEmail.isEmpty()) {
                            try {
                                response = client.findByEmail_JSON(String.class, parrainEmail);
                            } catch (Exception e) {
                            }

                            if (response != null) {
                                BonAchat ba = new BonAchat();
                                ba.setIdutilisateur(gson.fromJson(response, Utilisateur.class));

                                Calendar current = Calendar.getInstance();
                                current.setTime(new Date());
                                current.add(Calendar.DATE, 30);
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String exp = dateFormat.format(current.getTime());

                                ba.setDateExp(exp);
                                ba.setValeur(10);
                                bac.create_JSON(gson.toJson(ba));
                                bac.close();
                            }
                        }
                        try {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                        } catch (IOException ex) {
                            Logger.getLogger(UtilisateursBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cet EMail est déja utilisé !", "Cet EMail est déja utilisé"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Les mots de passe ne sont pas identiques !", "Les mots de passe ne sont pas identiques !"));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remplire touts les champs !", "Remplire touts les champs !"));
        }

        return null;
    }

    public String login(ActionEvent event) {
        String response = null;
        try {

            response = client.findByEmailPassword_JSON(String.class, user.getEmail(), user.getPassword());
            client.close();
        } catch (Exception e) {
        }
        if (response != null) {
            try {
                Gson gson = new Gson();
                user = gson.fromJson(response, Utilisateur.class);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", " Bienvenu " + user.getNom()));
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UtilisateursBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login ou mot de passe incorrecte !", "Login ou mot de passe incorrecte !"));
        }
        return null;
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

    public boolean isConditions() {
        return conditions;
    }

    public void setConditions(boolean conditions) {
        this.conditions = conditions;
    }

    public String getParrainEmail() {
        return parrainEmail;
    }

    public void setParrainEmail(String parrainEmail) {
        this.parrainEmail = parrainEmail;
    }
}