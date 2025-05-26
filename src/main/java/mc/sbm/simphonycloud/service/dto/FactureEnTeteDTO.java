package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.FactureEnTete} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureEnTeteDTO implements Serializable {

    @NotNull
    private Integer id;

    private Integer num;

    private String factureRef;

    private ZonedDateTime ouvertureDateTime;

    private ZonedDateTime fermetureDateTime;

    private Boolean estAnnule;

    private Integer nbrePax;

    private Integer numTable;

    private Integer taxeMontantTotal;

    private Integer sousTotal;

    private Integer factureTotal;

    private Integer commissionTotal;

    private Integer tipTotal;

    private Integer remiseTotal;

    private Integer erreursCorrigeesTotal;

    private Integer retourTotal;

    private Integer xferToFactureEnTeteRef;

    private String xferStatus;

    private Integer categoriePointDeVenteRef;

    private Integer pointDeVenteRef;

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

    public String getFactureRef() {
        return factureRef;
    }

    public void setFactureRef(String factureRef) {
        this.factureRef = factureRef;
    }

    public ZonedDateTime getOuvertureDateTime() {
        return ouvertureDateTime;
    }

    public void setOuvertureDateTime(ZonedDateTime ouvertureDateTime) {
        this.ouvertureDateTime = ouvertureDateTime;
    }

    public ZonedDateTime getFermetureDateTime() {
        return fermetureDateTime;
    }

    public void setFermetureDateTime(ZonedDateTime fermetureDateTime) {
        this.fermetureDateTime = fermetureDateTime;
    }

    public Boolean getEstAnnule() {
        return estAnnule;
    }

    public void setEstAnnule(Boolean estAnnule) {
        this.estAnnule = estAnnule;
    }

    public Integer getNbrePax() {
        return nbrePax;
    }

    public void setNbrePax(Integer nbrePax) {
        this.nbrePax = nbrePax;
    }

    public Integer getNumTable() {
        return numTable;
    }

    public void setNumTable(Integer numTable) {
        this.numTable = numTable;
    }

    public Integer getTaxeMontantTotal() {
        return taxeMontantTotal;
    }

    public void setTaxeMontantTotal(Integer taxeMontantTotal) {
        this.taxeMontantTotal = taxeMontantTotal;
    }

    public Integer getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(Integer sousTotal) {
        this.sousTotal = sousTotal;
    }

    public Integer getFactureTotal() {
        return factureTotal;
    }

    public void setFactureTotal(Integer factureTotal) {
        this.factureTotal = factureTotal;
    }

    public Integer getCommissionTotal() {
        return commissionTotal;
    }

    public void setCommissionTotal(Integer commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    public Integer getTipTotal() {
        return tipTotal;
    }

    public void setTipTotal(Integer tipTotal) {
        this.tipTotal = tipTotal;
    }

    public Integer getRemiseTotal() {
        return remiseTotal;
    }

    public void setRemiseTotal(Integer remiseTotal) {
        this.remiseTotal = remiseTotal;
    }

    public Integer getErreursCorrigeesTotal() {
        return erreursCorrigeesTotal;
    }

    public void setErreursCorrigeesTotal(Integer erreursCorrigeesTotal) {
        this.erreursCorrigeesTotal = erreursCorrigeesTotal;
    }

    public Integer getRetourTotal() {
        return retourTotal;
    }

    public void setRetourTotal(Integer retourTotal) {
        this.retourTotal = retourTotal;
    }

    public Integer getXferToFactureEnTeteRef() {
        return xferToFactureEnTeteRef;
    }

    public void setXferToFactureEnTeteRef(Integer xferToFactureEnTeteRef) {
        this.xferToFactureEnTeteRef = xferToFactureEnTeteRef;
    }

    public String getXferStatus() {
        return xferStatus;
    }

    public void setXferStatus(String xferStatus) {
        this.xferStatus = xferStatus;
    }

    public Integer getCategoriePointDeVenteRef() {
        return categoriePointDeVenteRef;
    }

    public void setCategoriePointDeVenteRef(Integer categoriePointDeVenteRef) {
        this.categoriePointDeVenteRef = categoriePointDeVenteRef;
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
        if (!(o instanceof FactureEnTeteDTO)) {
            return false;
        }

        FactureEnTeteDTO factureEnTeteDTO = (FactureEnTeteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureEnTeteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureEnTeteDTO{" +
            "id=" + getId() +
            ", num=" + getNum() +
            ", factureRef='" + getFactureRef() + "'" +
            ", ouvertureDateTime='" + getOuvertureDateTime() + "'" +
            ", fermetureDateTime='" + getFermetureDateTime() + "'" +
            ", estAnnule='" + getEstAnnule() + "'" +
            ", nbrePax=" + getNbrePax() +
            ", numTable=" + getNumTable() +
            ", taxeMontantTotal=" + getTaxeMontantTotal() +
            ", sousTotal=" + getSousTotal() +
            ", factureTotal=" + getFactureTotal() +
            ", commissionTotal=" + getCommissionTotal() +
            ", tipTotal=" + getTipTotal() +
            ", remiseTotal=" + getRemiseTotal() +
            ", erreursCorrigeesTotal=" + getErreursCorrigeesTotal() +
            ", retourTotal=" + getRetourTotal() +
            ", xferToFactureEnTeteRef=" + getXferToFactureEnTeteRef() +
            ", xferStatus='" + getXferStatus() + "'" +
            ", categoriePointDeVenteRef=" + getCategoriePointDeVenteRef() +
            ", pointDeVenteRef=" + getPointDeVenteRef() +
            "}";
    }
}
