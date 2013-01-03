/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anas
 */
@Entity
@Table(name = "commentaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commentaire.findAll", query = "SELECT c FROM Commentaire c")})
public class Commentaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcommentaire")
    private Integer idcommentaire;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "date")
    private String date;
    @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idutilisateur;
    @JoinColumn(name = "iddeal", referencedColumnName = "iddeal")
    @ManyToOne(optional = false)
    private Deal iddeal;

    public Commentaire() {
    }

    public Commentaire(Integer idcommentaire) {
        this.idcommentaire = idcommentaire;
    }

    public Commentaire(Integer idcommentaire, String message, String date) {
        this.idcommentaire = idcommentaire;
        this.message = message;
        this.date = date;
    }

    public Integer getIdcommentaire() {
        return idcommentaire;
    }

    public void setIdcommentaire(Integer idcommentaire) {
        this.idcommentaire = idcommentaire;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Utilisateur getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Utilisateur idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Deal getIddeal() {
        return iddeal;
    }

    public void setIddeal(Deal iddeal) {
        this.iddeal = iddeal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcommentaire != null ? idcommentaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commentaire)) {
            return false;
        }
        Commentaire other = (Commentaire) object;
        if ((this.idcommentaire == null && other.idcommentaire != null) || (this.idcommentaire != null && !this.idcommentaire.equals(other.idcommentaire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gbuy.entities.Commentaire[ idcommentaire=" + idcommentaire + " ]";
    }
    
}
