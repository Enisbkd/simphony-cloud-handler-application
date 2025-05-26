package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Societe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SocieteDTO implements Serializable {

    @NotNull
    private String id;

    private String nom;

    private String nomCourt;

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

    public String getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocieteDTO)) {
            return false;
        }

        SocieteDTO societeDTO = (SocieteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, societeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocieteDTO{" +
            "id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            "}";
    }
}
