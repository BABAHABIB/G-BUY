package com.gbuy.beans;

import com.gbuy.clients.BonAchatsClient;
import com.gbuy.clients.UtilisateurClient;
import com.gbuy.entities.BonAchat;
import com.gbuy.entities.Utilisateur;
import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Anas
 */
@ManagedBean(name = "register")
@RequestScoped
public class Register {

    private String password;
    private boolean conditions;
    private String parrainEmail;
    private Utilisateur user = new Utilisateur();
    private UtilisateurClient client = new UtilisateurClient();
    private String message = "Les champs marquer par * sont obligatoire !";

    public String adduser() {
        if (conditions) 
        {
            if (!user.getEmail().isEmpty() && !user.getNom().isEmpty()
                    && !user.getPrenom().isEmpty() && !user.getAdresse().isEmpty()
                    && !user.getVille().isEmpty() && !user.getPays().isEmpty()) 
            {
                if (user.getPassword().equals(password)) 
                {
                    String response = null;
                    try {
                        response = client.findByEmail_JSON(String.class, user.getEmail());
                    } catch (Exception e) {
                    }
                    if (response == null) 
                    {
                        Gson gson = new Gson();
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

                                BonAchatsClient bac = new BonAchatsClient();
                                bac.create_JSON(gson.toJson(ba));
                            }
                        }
                        return "index.xhtml";
                    }
                    else message = "Cet email est déja utilisé !";
                }
                else message = "Les deux mots de passe ne sont pas identiques !";
            }
        }else message = "Veuillez acceptez nos condictions !";
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

    public String getParrainEmail() {
        return parrainEmail;
    }

    public void setParrainEmail(String parrainEmail) {
        this.parrainEmail = parrainEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}