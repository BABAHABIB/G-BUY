/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anas
 */
@Entity
@Table(name = "commande")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c")})
public class Commande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcommande")
    private Integer idcommande;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")
    @ManyToOne(optional = false)
    private Utilisateur idutilisateur;
    @JoinColumn(name = "iddeal", referencedColumnName = "iddeal")
    @ManyToOne(optional = false)
    private Deal iddeal;
    @JoinColumn(name = "idbon_achat", referencedColumnName = "idbon_achat")
    @ManyToOne
    private BonAchat idbonAchat;

    public Commande() {
    }

    public Commande(Integer idcommande) {
        this.idcommande = idcommande;
    }

    public Commande(Integer idcommande, Date date) {
        this.idcommande = idcommande;
        this.date = date;
    }

    public Integer getIdcommande() {
        return idcommande;
    }

    public void setIdcommande(Integer idcommande) {
        this.idcommande = idcommande;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public BonAchat getIdbonAchat() {
        return idbonAchat;
    }

    public void setIdbonAchat(BonAchat idbonAchat) {
        this.idbonAchat = idbonAchat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcommande != null ? idcommande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande)) {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.idcommande == null && other.idcommande != null) || (this.idcommande != null && !this.idcommande.equals(other.idcommande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gbuy.entities.Commande[ idcommande=" + idcommande + " ]";
    }
    
}
