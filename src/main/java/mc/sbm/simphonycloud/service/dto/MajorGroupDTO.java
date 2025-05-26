package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.MajorGroup} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MajorGroupDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String nom;

    private String nomCourt;

    @NotNull
    private Integer pointDeVenteRef;

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

    public Integer getPointDeVenteRef() {
        return pointDeVenteRef;
    }

    public void setPointDeVenteRef(Integer pointDeVenteRef) {
        this.pointDeVenteRef = pointDeVenteRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MajorGroupDTO)) {
            return false;
        }

        MajorGroupDTO majorGroupDTO = (MajorGroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, majorGroupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MajorGroupDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", pointDeVenteRef=" + getPointDeVenteRef() +
            "}";
    }
}
