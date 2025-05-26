package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hier.
 */
@Entity
@Table(name = "hier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hier implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "parent_hier_id")
    private String parentHierId;

    @Column(name = "unit_id")
    private String unitId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Hier id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Hier nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getParentHierId() {
        return this.parentHierId;
    }

    public Hier parentHierId(String parentHierId) {
        this.setParentHierId(parentHierId);
        return this;
    }

    public void setParentHierId(String parentHierId) {
        this.parentHierId = parentHierId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public Hier unitId(String unitId) {
        this.setUnitId(unitId);
        return this;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hier)) {
            return false;
        }
        return getId() != null && getId().equals(((Hier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hier{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", parentHierId='" + getParentHierId() + "'" +
            ", unitId='" + getUnitId() + "'" +
            "}";
    }
}
