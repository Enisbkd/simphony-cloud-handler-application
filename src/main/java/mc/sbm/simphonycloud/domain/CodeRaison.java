package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CodeRaison.
 */
@Entity
@Table(name = "code_raison")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeRaison implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "nom_court", nullable = false)
    private Integer nomCourt;

    @Column(name = "nom_mstr")
    private String nomMstr;

    @Column(name = "num_mstr")
    private Integer numMstr;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "etablissement_ref", nullable = false)
    private String etablissementRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public CodeRaison id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNomCourt() {
        return this.nomCourt;
    }

    public CodeRaison nomCourt(Integer nomCourt) {
        this.setNomCourt(nomCourt);
        return this;
    }

    public void setNomCourt(Integer nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNomMstr() {
        return this.nomMstr;
    }

    public CodeRaison nomMstr(String nomMstr) {
        this.setNomMstr(nomMstr);
        return this;
    }

    public void setNomMstr(String nomMstr) {
        this.nomMstr = nomMstr;
    }

    public Integer getNumMstr() {
        return this.numMstr;
    }

    public CodeRaison numMstr(Integer numMstr) {
        this.setNumMstr(numMstr);
        return this;
    }

    public void setNumMstr(Integer numMstr) {
        this.numMstr = numMstr;
    }

    public String getName() {
        return this.name;
    }

    public CodeRaison name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtablissementRef() {
        return this.etablissementRef;
    }

    public CodeRaison etablissementRef(String etablissementRef) {
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
        if (!(o instanceof CodeRaison)) {
            return false;
        }
        return getId() != null && getId().equals(((CodeRaison) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeRaison{" +
            "id=" + getId() +
            ", nomCourt=" + getNomCourt() +
            ", nomMstr='" + getNomMstr() + "'" +
            ", numMstr=" + getNumMstr() +
            ", name='" + getName() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
