package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Remise.
 */
@Entity
@Table(name = "remise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Remise implements Serializable {

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

    @Column(name = "nom_mstr")
    private String nomMstr;

    @Column(name = "type_value")
    private String typeValue;

    @Column(name = "value")
    private Float value;

    @NotNull
    @Column(name = "point_de_vente_ref", nullable = false)
    private Integer pointDeVenteRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public Remise id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Remise nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return this.nomCourt;
    }

    public Remise nomCourt(String nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNomMstr() {
        return this.nomMstr;
    }

    public Remise nomMstr(String nomMstr) {
        this.setNomMstr(nomMstr);
        return this;
    }

    public void setNomMstr(String nomMstr) {
        this.nomMstr = nomMstr;
    }

    public String getTypeValue() {
        return this.typeValue;
    }

    public Remise typeValue(String typeValue) {
        this.setTypeValue(typeValue);
        return this;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Float getValue() {
        return this.value;
    }

    public Remise value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getPointDeVenteRef() {
        return this.pointDeVenteRef;
    }

    public Remise pointDeVenteRef(Integer pointDeVenteRef) {
        this.setPointDeVenteRef(pointDeVenteRef);
        return this;
    }

    public void setPointDeVenteRef(Integer pointDeVenteRef) {
        this.pointDeVenteRef = pointDeVenteRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remise)) {
            return false;
        }
        return getId() != null && getId().equals(((Remise) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Remise{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", nomMstr='" + getNomMstr() + "'" +
            ", typeValue='" + getTypeValue() + "'" +
            ", value=" + getValue() +
            ", pointDeVenteRef=" + getPointDeVenteRef() +
            "}";
    }
}
