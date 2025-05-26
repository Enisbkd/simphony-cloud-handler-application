package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Remise} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemiseDTO implements Serializable {

    @NotNull
    private Integer id;

    private String nom;

    private String nomCourt;

    private String nomMstr;

    private String typeValue;

    private Float value;

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

    public String getNomMstr() {
        return nomMstr;
    }

    public void setNomMstr(String nomMstr) {
        this.nomMstr = nomMstr;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
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
        if (!(o instanceof RemiseDTO)) {
            return false;
        }

        RemiseDTO remiseDTO = (RemiseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, remiseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemiseDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", nomMstr='" + getNomMstr() + "'" +
            ", typeValue='" + getTypeValue() + "'" +
            ", value=" + getValue() +
            ", pointDeVenteRef=" + getPointDeVenteRef() +
            "}";
    }
}
