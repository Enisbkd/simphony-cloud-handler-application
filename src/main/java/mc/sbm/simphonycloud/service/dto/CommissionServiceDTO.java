package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.CommissionService} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommissionServiceDTO implements Serializable {

    @NotNull
    private Integer id;

    private String nom;

    private String nomCourt;

    private String typeValue;

    private Float value;

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

    public String getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(String nomCourt) {
        this.nomCourt = nomCourt;
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
        if (!(o instanceof CommissionServiceDTO)) {
            return false;
        }

        CommissionServiceDTO commissionServiceDTO = (CommissionServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commissionServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommissionServiceDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", typeValue='" + getTypeValue() + "'" +
            ", value=" + getValue() +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
