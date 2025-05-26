package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Taxe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaxeDTO implements Serializable {

    @NotNull
    private Integer id;

    private String nom;

    private String nomCourt;

    private Integer vatTaxRate;

    private Integer classId;

    private Integer taxType;

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

    public Integer getVatTaxRate() {
        return vatTaxRate;
    }

    public void setVatTaxRate(Integer vatTaxRate) {
        this.vatTaxRate = vatTaxRate;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
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
        if (!(o instanceof TaxeDTO)) {
            return false;
        }

        TaxeDTO taxeDTO = (TaxeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taxeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaxeDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", vatTaxRate=" + getVatTaxRate() +
            ", classId=" + getClassId() +
            ", taxType=" + getTaxType() +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
