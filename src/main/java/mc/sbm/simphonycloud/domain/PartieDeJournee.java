package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PartieDeJournee.
 */
@Entity
@Table(name = "partie_de_journee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartieDeJournee implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "time_range_start")
    private String timeRangeStart;

    @Column(name = "time_range_end")
    private String timeRangeEnd;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public PartieDeJournee id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeRangeStart() {
        return this.timeRangeStart;
    }

    public PartieDeJournee timeRangeStart(String timeRangeStart) {
        this.setTimeRangeStart(timeRangeStart);
        return this;
    }

    public void setTimeRangeStart(String timeRangeStart) {
        this.timeRangeStart = timeRangeStart;
    }

    public String getTimeRangeEnd() {
        return this.timeRangeEnd;
    }

    public PartieDeJournee timeRangeEnd(String timeRangeEnd) {
        this.setTimeRangeEnd(timeRangeEnd);
        return this;
    }

    public void setTimeRangeEnd(String timeRangeEnd) {
        this.timeRangeEnd = timeRangeEnd;
    }

    public String getNom() {
        return this.nom;
    }

    public PartieDeJournee nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartieDeJournee)) {
            return false;
        }
        return getId() != null && getId().equals(((PartieDeJournee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartieDeJournee{" +
            "id=" + getId() +
            ", timeRangeStart='" + getTimeRangeStart() + "'" +
            ", timeRangeEnd='" + getTimeRangeEnd() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
