package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.CategoriePointDeVente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoriePointDeVenteDTO implements Serializable {

    @NotNull
    private Integer id;

    private String categorie;

    private Integer etablissementRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getEtablissementRef() {
        return etablissementRef;
    }

    public void setEtablissementRef(Integer etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoriePointDeVenteDTO)) {
            return false;
        }

        CategoriePointDeVenteDTO categoriePointDeVenteDTO = (CategoriePointDeVenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoriePointDeVenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriePointDeVenteDTO{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", etablissementRef=" + getEtablissementRef() +
            "}";
    }
}
