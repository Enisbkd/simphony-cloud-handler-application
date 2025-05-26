package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategoriePointDeVente.
 */
@Entity
@Table(name = "categorie_point_de_vente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriePointDeVente implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "etablissement_ref")
    private Integer etablissementRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public CategoriePointDeVente id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public CategoriePointDeVente categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getEtablissementRef() {
        return this.etablissementRef;
    }

    public CategoriePointDeVente etablissementRef(Integer etablissementRef) {
        this.setEtablissementRef(etablissementRef);
        return this;
    }

    public void setEtablissementRef(Integer etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriePointDeVente)) {
            return false;
        }
        return getId() != null && getId().equals(((CategoriePointDeVente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriePointDeVente{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", etablissementRef=" + getEtablissementRef() +
            "}";
    }
}
