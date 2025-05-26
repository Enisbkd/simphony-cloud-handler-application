package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Hier} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HierDTO implements Serializable {

    @NotNull
    private String id;

    @NotNull
    private String nom;

    private String parentHierId;

    private String unitId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getParentHierId() {
        return parentHierId;
    }

    public void setParentHierId(String parentHierId) {
        this.parentHierId = parentHierId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HierDTO)) {
            return false;
        }

        HierDTO hierDTO = (HierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HierDTO{" +
            "id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", parentHierId='" + getParentHierId() + "'" +
            ", unitId='" + getUnitId() + "'" +
            "}";
    }
}
