package com.inti.project.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.inti.project.domain.enumeration.Caracteristiques;

/**
 * A Personne.
 */
@Entity
@Table(name = "personne")
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @NotNull
    @Column(name = "genre", nullable = false)
    private String genre;

    @NotNull
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @NotNull
    @Column(name = "naissance", nullable = false)
    private String naissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "list_carac")
    private Caracteristiques listCarac;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Personne nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Personne prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public Personne mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGenre() {
        return genre;
    }

    public Personne genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Personne motDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
        return this;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNaissance() {
        return naissance;
    }

    public Personne naissance(String naissance) {
        this.naissance = naissance;
        return this;
    }

    public void setNaissance(String naissance) {
        this.naissance = naissance;
    }

    public Caracteristiques getListCarac() {
        return listCarac;
    }

    public Personne listCarac(Caracteristiques listCarac) {
        this.listCarac = listCarac;
        return this;
    }

    public void setListCarac(Caracteristiques listCarac) {
        this.listCarac = listCarac;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", mail='" + getMail() + "'" +
            ", genre='" + getGenre() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            ", naissance='" + getNaissance() + "'" +
            ", listCarac='" + getListCarac() + "'" +
            "}";
    }
}
