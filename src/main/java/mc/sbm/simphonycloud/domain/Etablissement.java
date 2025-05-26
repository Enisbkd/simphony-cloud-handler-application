package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "est_groupe")
    private Boolean estGroupe;

    @Column(name = "source_version")
    private String sourceVersion;

    @NotNull
    @Column(name = "societe_ref", nullable = false)
    private String societeRef;

    @NotNull
    @Column(name = "hier_ref", nullable = false)
    private String hierRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Etablissement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Etablissement nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getEstGroupe() {
        return this.estGroupe;
    }

    public Etablissement estGroupe(Boolean estGroupe) {
        this.setEstGroupe(estGroupe);
        return this;
    }

    public void setEstGroupe(Boolean estGroupe) {
        this.estGroupe = estGroupe;
    }

    public String getSourceVersion() {
        return this.sourceVersion;
    }

    public Etablissement sourceVersion(String sourceVersion) {
        this.setSourceVersion(sourceVersion);
        return this;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public String getSocieteRef() {
        return this.societeRef;
    }

    public Etablissement societeRef(String societeRef) {
        this.setSocieteRef(societeRef);
        return this;
    }

    public void setSocieteRef(String societeRef) {
        this.societeRef = societeRef;
    }

    public String getHierRef() {
        return this.hierRef;
    }

    public Etablissement hierRef(String hierRef) {
        this.setHierRef(hierRef);
        return this;
    }

    public void setHierRef(String hierRef) {
        this.hierRef = hierRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return getId() != null && getId().equals(((Etablissement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", estGroupe='" + getEstGroupe() + "'" +
            ", sourceVersion='" + getSourceVersion() + "'" +
            ", societeRef='" + getSocieteRef() + "'" +
            ", hierRef='" + getHierRef() + "'" +
            "}";
    }
}
