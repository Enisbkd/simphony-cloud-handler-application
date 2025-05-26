package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.FamilyGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FamilyGroupDTO implements Serializable {

    @NotNull
    private Integer id;

    private String nom;

    @NotNull
    private String nomCourt;

    private Integer majorGroupRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    public Integer getMajorGroupRef() {
        return majorGroupRef;
    }

    public void setMajorGroupRef(Integer majorGroupRef) {
        this.majorGroupRef = majorGroupRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyGroupDTO)) {
            return false;
        }

        FamilyGroupDTO familyGroupDTO = (FamilyGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, familyGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyGroupDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", majorGroupRef=" + getMajorGroupRef() +
            "}";
    }
}
