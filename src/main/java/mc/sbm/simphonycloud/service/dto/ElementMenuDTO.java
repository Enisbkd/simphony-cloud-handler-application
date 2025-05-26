package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.ElementMenu} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ElementMenuDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer masterId;

    @NotNull
    private String nom;

    private String nomCourt;

    @NotNull
    private Integer familyGroupRef;

    private Integer prix;

    @NotNull
    private Integer menuRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
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

    public Integer getFamilyGroupRef() {
        return familyGroupRef;
    }

    public void setFamilyGroupRef(Integer familyGroupRef) {
        this.familyGroupRef = familyGroupRef;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getMenuRef() {
        return menuRef;
    }

    public void setMenuRef(Integer menuRef) {
        this.menuRef = menuRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementMenuDTO)) {
            return false;
        }

        ElementMenuDTO elementMenuDTO = (ElementMenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, elementMenuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementMenuDTO{" +
            "id=" + getId() +
            ", masterId=" + getMasterId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", familyGroupRef=" + getFamilyGroupRef() +
            ", prix=" + getPrix() +
            ", menuRef=" + getMenuRef() +
            "}";
    }
}
