package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Menu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MenuDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String nom;

    private String description;

    @NotNull
    private String etablissementRef;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtablissementRef() {
        return etablissementRef;
    }

    public void setEtablissementRef(String etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDTO)) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
