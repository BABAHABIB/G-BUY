/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gbuy.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anas
 */
@Entity
@Table(name = "deal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deal.findAll", query = "SELECT d FROM Deal d")})
public class Deal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddeal")
    private Integer iddeal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "titre")
    private String titre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prix_habituel")
    private double prixHabituel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prix")
    private double prix;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_exp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_ajout")
    @Temporal(TemporalType.DATE)
    private Date dateAjout;
    @Size(max = 45)
    @Column(name = "conditions")
    private String conditions;
    @Size(max = 45)
    @Column(name = "image")
    private String image;
    @Size(max = 45)
    @Column(name = "video")
    private String video;
    @Size(max = 45)
    @Column(name = "tags")
    private String tags;
    @Size(max = 45)
    @Column(name = "adresse")
    private String adresse;
    @Size(max = 45)
    @Column(name = "ville")
    private String ville;
    @Size(max = 45)
    @Column(name = "pays")
    private String pays;
    @Size(max = 45)
    @Column(name = "code_postale")
    private String codePostale;
    @Size(max = 45)
    @Column(name = "cadeau")
    private String cadeau;
    @JoinTable(name = "rate", joinColumns = {
        @JoinColumn(name = "iddeal", referencedColumnName = "iddeal")}, inverseJoinColumns = {
        @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")})
    @ManyToMany
    private Collection<Utilisateur> utilisateurCollection;
    @JoinColumn(name = "prestataire", referencedColumnName = "idprestataire")
    @ManyToOne
    private Prestataire prestataire;
    @JoinColumn(name = "categorie", referencedColumnName = "idcategorie")
    @ManyToOne(optional = false)
    private Categorie categorie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddeal")
    private Collection<Commande> commandeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddeal")
    private Collection<Commentaire> commentaireCollection;

    public Deal() {
    }

    public Deal(Integer iddeal) {
        this.iddeal = iddeal;
    }

    public Deal(Integer iddeal, String titre, String description, double prixHabituel, double prix, Date dateExp, Date dateAjout) {
        this.iddeal = iddeal;
        this.titre = titre;
        this.description = description;
        this.prixHabituel = prixHabituel;
        this.prix = prix;
        this.dateExp = dateExp;
        this.dateAjout = dateAjout;
    }

    public Integer getIddeal() {
        return iddeal;
    }

    public void setIddeal(Integer iddeal) {
        this.iddeal = iddeal;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixHabituel() {
        return prixHabituel;
    }

    public void setPrixHabituel(double prixHabituel) {
        this.prixHabituel = prixHabituel;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDateExp() {
        return dateExp;
    }

    public void setDateExp(Date dateExp) {
        this.dateExp = dateExp;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getCodePostale() {
        return codePostale;
    }

    public void setCodePostale(String codePostale) {
        this.codePostale = codePostale;
    }

    public String getCadeau() {
        return cadeau;
    }

    public void setCadeau(String cadeau) {
        this.cadeau = cadeau;
    }

    @XmlTransient
    public Collection<Utilisateur> getUtilisateurCollection() {
        return utilisateurCollection;
    }

    public void setUtilisateurCollection(Collection<Utilisateur> utilisateurCollection) {
        this.utilisateurCollection = utilisateurCollection;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @XmlTransient
    public Collection<Commande> getCommandeCollection() {
        return commandeCollection;
    }

    public void setCommandeCollection(Collection<Commande> commandeCollection) {
        this.commandeCollection = commandeCollection;
    }

    @XmlTransient
    public Collection<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(Collection<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeal != null ? iddeal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deal)) {
            return false;
        }
        Deal other = (Deal) object;
        if ((this.iddeal == null && other.iddeal != null) || (this.iddeal != null && !this.iddeal.equals(other.iddeal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gbuy.entities.Deal[ iddeal=" + iddeal + " ]";
    }
    
}
