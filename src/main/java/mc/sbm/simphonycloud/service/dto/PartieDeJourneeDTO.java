package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.PartieDeJournee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartieDeJourneeDTO implements Serializable {

    @NotNull
    private Integer id;

    private String timeRangeStart;

    private String timeRangeEnd;

    @NotNull
    private String nom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeRangeStart() {
        return timeRangeStart;
    }

    public void setTimeRangeStart(String timeRangeStart) {
        this.timeRangeStart = timeRangeStart;
    }

    public String getTimeRangeEnd() {
        return timeRangeEnd;
    }

    public void setTimeRangeEnd(String timeRangeEnd) {
        this.timeRangeEnd = timeRangeEnd;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartieDeJourneeDTO)) {
            return false;
        }

        PartieDeJourneeDTO partieDeJourneeDTO = (PartieDeJourneeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partieDeJourneeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartieDeJourneeDTO{" +
            "id=" + getId() +
            ", timeRangeStart='" + getTimeRangeStart() + "'" +
            ", timeRangeEnd='" + getTimeRangeEnd() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
