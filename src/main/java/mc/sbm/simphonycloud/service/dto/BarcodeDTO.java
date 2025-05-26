package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Barcode} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BarcodeDTO implements Serializable {

    @NotNull
    private Integer id;

    private Integer num;

    private String barcode;

    private Integer prix;

    private Integer coutPreparation;

    private Integer defNumSequence;

    private Integer prixNumSequence;

    @NotNull
    private Integer pointDeVenteRef;

    private Integer elementMenuRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getCoutPreparation() {
        return coutPreparation;
    }

    public void setCoutPreparation(Integer coutPreparation) {
        this.coutPreparation = coutPreparation;
    }

    public Integer getDefNumSequence() {
        return defNumSequence;
    }

    public void setDefNumSequence(Integer defNumSequence) {
        this.defNumSequence = defNumSequence;
    }

    public Integer getPrixNumSequence() {
        return prixNumSequence;
    }

    public void setPrixNumSequence(Integer prixNumSequence) {
        this.prixNumSequence = prixNumSequence;
    }

    public Integer getPointDeVenteRef() {
        return pointDeVenteRef;
    }

    public void setPointDeVenteRef(Integer pointDeVenteRef) {
        this.pointDeVenteRef = pointDeVenteRef;
    }

    public Integer getElementMenuRef() {
        return elementMenuRef;
    }

    public void setElementMenuRef(Integer elementMenuRef) {
        this.elementMenuRef = elementMenuRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BarcodeDTO)) {
            return false;
        }

        BarcodeDTO barcodeDTO = (BarcodeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, barcodeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BarcodeDTO{" +
            "id=" + getId() +
            ", num=" + getNum() +
            ", barcode='" + getBarcode() + "'" +
            ", prix=" + getPrix() +
            ", coutPreparation=" + getCoutPreparation() +
            ", defNumSequence=" + getDefNumSequence() +
            ", prixNumSequence=" + getPrixNumSequence() +
            ", pointDeVenteRef=" + getPointDeVenteRef() +
            ", elementMenuRef=" + getElementMenuRef() +
            "}";
    }
}
