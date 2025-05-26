package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Taxe.
 */
@Entity
@Table(name = "taxe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Taxe implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "nom_court")
    private String nomCourt;

    @Column(name = "vat_tax_rate")
    private Integer vatTaxRate;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "tax_type")
    private Integer taxType;

    @NotNull
    @Column(name = "etablissement_ref", nullable = false)
    private String etablissementRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public Taxe id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Taxe nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return this.nomCourt;
    }

    public Taxe nomCourt(String nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public Integer getVatTaxRate() {
        return this.vatTaxRate;
    }

    public Taxe vatTaxRate(Integer vatTaxRate) {
        this.setVatTaxRate(vatTaxRate);
        return this;
    }

    public void setVatTaxRate(Integer vatTaxRate) {
        this.vatTaxRate = vatTaxRate;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public Taxe classId(Integer classId) {
        this.setClassId(classId);
        return this;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTaxType() {
        return this.taxType;
    }

    public Taxe taxType(Integer taxType) {
        this.setTaxType(taxType);
        return this;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    public String getEtablissementRef() {
        return this.etablissementRef;
    }

    public Taxe etablissementRef(String etablissementRef) {
        this.setEtablissementRef(etablissementRef);
        return this;
    }

    public void setEtablissementRef(String etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taxe)) {
            return false;
        }
        return getId() != null && getId().equals(((Taxe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taxe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", vatTaxRate=" + getVatTaxRate() +
            ", classId=" + getClassId() +
            ", taxType=" + getTaxType() +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
