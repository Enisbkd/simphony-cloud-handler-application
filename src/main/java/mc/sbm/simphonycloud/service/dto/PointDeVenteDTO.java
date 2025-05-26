package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.PointDeVente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointDeVenteDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String nom;

    private String nomCourt;

    private Boolean estActif;

    @NotNull
    private String etablissementRef;

    @NotNull
    private String hierRef;

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

    public Boolean getEstActif() {
        return estActif;
    }

    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }

    public String getEtablissementRef() {
        return etablissementRef;
    }

    public void setEtablissementRef(String etablissementRef) {
        this.etablissementRef = etablissementRef;
    }

    public String getHierRef() {
        return hierRef;
    }

    public void setHierRef(String hierRef) {
        this.hierRef = hierRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointDeVenteDTO)) {
            return false;
        }

        PointDeVenteDTO pointDeVenteDTO = (PointDeVenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointDeVenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointDeVenteDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", estActif='" + getEstActif() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            ", hierRef='" + getHierRef() + "'" +
            "}";
    }
}
