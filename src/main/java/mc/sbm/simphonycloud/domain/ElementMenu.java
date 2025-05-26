package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ElementMenu.
 */
@Entity
@Table(name = "element_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ElementMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "master_id", nullable = false)
    private Integer masterId;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "nom_court")
    private String nomCourt;

    @NotNull
    @Column(name = "family_group_ref", nullable = false)
    private Integer familyGroupRef;

    @Column(name = "prix")
    private Integer prix;

    @NotNull
    @Column(name = "menu_ref", nullable = false)
    private Integer menuRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public ElementMenu id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterId() {
        return this.masterId;
    }

    public ElementMenu masterId(Integer masterId) {
        this.setMasterId(masterId);
        return this;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getNom() {
        return this.nom;
    }

    public ElementMenu nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return this.nomCourt;
    }

    public ElementMenu nomCourt(String nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public Integer getFamilyGroupRef() {
        return this.familyGroupRef;
    }

    public ElementMenu familyGroupRef(Integer familyGroupRef) {
        this.setFamilyGroupRef(familyGroupRef);
        return this;
    }

    public void setFamilyGroupRef(Integer familyGroupRef) {
        this.familyGroupRef = familyGroupRef;
    }

    public Integer getPrix() {
        return this.prix;
    }

    public ElementMenu prix(Integer prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getMenuRef() {
        return this.menuRef;
    }

    public ElementMenu menuRef(Integer menuRef) {
        this.setMenuRef(menuRef);
        return this;
    }

    public void setMenuRef(Integer menuRef) {
        this.menuRef = menuRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementMenu)) {
            return false;
        }
        return getId() != null && getId().equals(((ElementMenu) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementMenu{" +
            "id=" + getId() +
            ", masterId=" + getMasterId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", familyGroupRef=" + getFamilyGroupRef() +
            ", prix=" + getPrix() +
            ", menuRef=" + getMenuRef() +
            "}";
    }
}
