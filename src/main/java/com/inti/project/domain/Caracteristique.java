package com.inti.project.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import com.inti.project.domain.enumeration.Caracteristiques;

/**
 * A Caracteristique.
 */
@Entity
@Table(name = "caracteristique")
public class Caracteristique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "carac")
    private Caracteristiques carac;

    @ManyToOne
    @JsonIgnoreProperties("listCaracs")
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Caracteristiques getCarac() {
        return carac;
    }

    public Caracteristique carac(Caracteristiques carac) {
        this.carac = carac;
        return this;
    }

    public void setCarac(Caracteristiques carac) {
        this.carac = carac;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Caracteristique personne(Personne personne) {
        this.personne = personne;
        return this;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Caracteristique)) {
            return false;
        }
        return id != null && id.equals(((Caracteristique) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Caracteristique{" +
            "id=" + getId() +
            ", carac='" + getCarac() + "'" +
            "}";
    }
}
