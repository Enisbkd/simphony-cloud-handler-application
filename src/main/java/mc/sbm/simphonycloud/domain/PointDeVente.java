package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PointDeVente.
 */
@Entity
@Table(name = "point_de_vente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointDeVente implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "nom_court")
    private String nomCourt;

    @Column(name = "est_actif")
    private Boolean estActif;

    @NotNull
    @Column(name = "etablissement_ref", nullable = false)
    private String etablissementRef;

    @NotNull
    @Column(name = "hier_ref", nullable = false)
    private String hierRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public PointDeVente id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public PointDeVente nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return this.nomCourt;
    }

    public PointDeVente nomCourt(String nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public Boolean getEstActif() {
        return this.estActif;
    }

    public PointDeVente estActif(Boolean estActif) {
        this.setEstActif(estActif);
        return this;
    }

    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }

    public String getEtablissementRef() {
        return this.etablissementRef;
    }

    public PointDeVente etablissementRef(String etablissementRef) {
        this.setEtablissementRef(etablissementRef);
        return this;
    }

    public void setEtablissementRef(String etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    public String getHierRef() {
        return this.hierRef;
    }

    public PointDeVente hierRef(String hierRef) {
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
        if (!(o instanceof PointDeVente)) {
            return false;
        }
        return getId() != null && getId().equals(((PointDeVente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointDeVente{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", estActif='" + getEstActif() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            ", hierRef='" + getHierRef() + "'" +
            "}";
    }
}
