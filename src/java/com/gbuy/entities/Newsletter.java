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
@Table(name = "newsletter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Newsletter.findAll", query = "SELECT n FROM Newsletter n")})
public class Newsletter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnewsletter")
    private Integer idnewsletter;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;

    public Newsletter() {
    }

    public Newsletter(Integer idnewsletter) {
        this.idnewsletter = idnewsletter;
    }

    public Newsletter(Integer idnewsletter, String email) {
        this.idnewsletter = idnewsletter;
        this.email = email;
    }

    public Integer getIdnewsletter() {
        return idnewsletter;
    }

    public void setIdnewsletter(Integer idnewsletter) {
        this.idnewsletter = idnewsletter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnewsletter != null ? idnewsletter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Newsletter)) {
            return false;
        }
        Newsletter other = (Newsletter) object;
        if ((this.idnewsletter == null && other.idnewsletter != null) || (this.idnewsletter != null && !this.idnewsletter.equals(other.idnewsletter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gbuy.entities.Newsletter[ idnewsletter=" + idnewsletter + " ]";
    }
    
}
