package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ModePaiement.
 */
@Entity
@Table(name = "mode_paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModePaiement implements Serializable {

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

    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "etablissement_ref", nullable = false)
    private String etablissementRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public ModePaiement id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ModePaiement nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return this.nomCourt;
    }

    public ModePaiement nomCourt(String nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNomMstr() {
        return this.nomMstr;
    }

    public ModePaiement nomMstr(String nomMstr) {
        this.setNomMstr(nomMstr);
        return this;
    }

    public void setNomMstr(String nomMstr) {
        this.nomMstr = nomMstr;
    }

    public String getType() {
        return this.type;
    }

    public ModePaiement type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtablissementRef() {
        return this.etablissementRef;
    }

    public ModePaiement etablissementRef(String etablissementRef) {
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
        if (!(o instanceof ModePaiement)) {
            return false;
        }
        return getId() != null && getId().equals(((ModePaiement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModePaiement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", nomMstr='" + getNomMstr() + "'" +
            ", type='" + getType() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
