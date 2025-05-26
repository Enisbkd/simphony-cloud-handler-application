package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.FactureDetail} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureDetailDTO implements Serializable {

    @NotNull
    private Integer id;

    private Integer factureEnTeteRef;

    private Integer numLigne;

    private String detailType;

    private Instant utcDateTime;

    private Instant lclDateTime;

    private String numSiege;

    private Float niveauPrix;

    private Float totalAffiche;

    private Integer quantiteAffiche;

    private Boolean estErreur;

    private Boolean estNul;

    private Boolean estRetourne;

    private Boolean estInvisible;

    private Integer totalLigne;

    private Integer codeRaisonRef;

    private Integer multiplicateur;

    private String referenceInfo;

    private String referenceInfo2;

    private Integer partieDeJourneeRef;

    private Integer periodeDeServiceRef;

    private Integer numChrono;

    private Integer parentFactureDetailRef;

    private Integer taxePourcentage;

    private Integer taxeMontant;

    private Integer modePaiementTotal;

    private Integer prix;

    private Integer transactionEmployeRef;

    private Integer transfertEmployeRef;

    private Integer managerEmployeRef;

    private Integer repasEmployeRef;

    private Integer remiseRef;

    private Integer remiseElementMenuRef;

    private Integer commissionServiceRef;

    private Integer modePaiementRef;

    private Integer elementMenuRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFactureEnTeteRef() {
        return factureEnTeteRef;
    }

    public void setFactureEnTeteRef(Integer factureEnTeteRef) {
        this.factureEnTeteRef = factureEnTeteRef;
    }

    public Integer getNumLigne() {
        return numLigne;
    }

    public void setNumLigne(Integer numLigne) {
        this.numLigne = numLigne;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public Instant getUtcDateTime() {
        return utcDateTime;
    }

    public void setUtcDateTime(Instant utcDateTime) {
        this.utcDateTime = utcDateTime;
    }

    public Instant getLclDateTime() {
        return lclDateTime;
    }

    public void setLclDateTime(Instant lclDateTime) {
        this.lclDateTime = lclDateTime;
    }

    public String getNumSiege() {
        return numSiege;
    }

    public void setNumSiege(String numSiege) {
        this.numSiege = numSiege;
    }

    public Float getNiveauPrix() {
        return niveauPrix;
    }

    public void setNiveauPrix(Float niveauPrix) {
        this.niveauPrix = niveauPrix;
    }

    public Float getTotalAffiche() {
        return totalAffiche;
    }

    public void setTotalAffiche(Float totalAffiche) {
        this.totalAffiche = totalAffiche;
    }

    public Integer getQuantiteAffiche() {
        return quantiteAffiche;
    }

    public void setQuantiteAffiche(Integer quantiteAffiche) {
        this.quantiteAffiche = quantiteAffiche;
    }

    public Boolean getEstErreur() {
        return estErreur;
    }

    public void setEstErreur(Boolean estErreur) {
        this.estErreur = estErreur;
    }

    public Boolean getEstNul() {
        return estNul;
    }

    public void setEstNul(Boolean estNul) {
        this.estNul = estNul;
    }

    public Boolean getEstRetourne() {
        return estRetourne;
    }

    public void setEstRetourne(Boolean estRetourne) {
        this.estRetourne = estRetourne;
    }

    public Boolean getEstInvisible() {
        return estInvisible;
    }

    public void setEstInvisible(Boolean estInvisible) {
        this.estInvisible = estInvisible;
    }

    public Integer getTotalLigne() {
        return totalLigne;
    }

    public void setTotalLigne(Integer totalLigne) {
        this.totalLigne = totalLigne;
    }

    public Integer getCodeRaisonRef() {
        return codeRaisonRef;
    }

    public void setCodeRaisonRef(Integer codeRaisonRef) {
        this.codeRaisonRef = codeRaisonRef;
    }

    public Integer getMultiplicateur() {
        return multiplicateur;
    }

    public void setMultiplicateur(Integer multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public String getReferenceInfo() {
        return referenceInfo;
    }

    public void setReferenceInfo(String referenceInfo) {
        this.referenceInfo = referenceInfo;
    }

    public String getReferenceInfo2() {
        return referenceInfo2;
    }

    public void setReferenceInfo2(String referenceInfo2) {
        this.referenceInfo2 = referenceInfo2;
    }

    public Integer getPartieDeJourneeRef() {
        return partieDeJourneeRef;
    }

    public void setPartieDeJourneeRef(Integer partieDeJourneeRef) {
        this.partieDeJourneeRef = partieDeJourneeRef;
    }

    public Integer getPeriodeDeServiceRef() {
        return periodeDeServiceRef;
    }

    public void setPeriodeDeServiceRef(Integer periodeDeServiceRef) {
        this.periodeDeServiceRef = periodeDeServiceRef;
    }

    public Integer getNumChrono() {
        return numChrono;
    }

    public void setNumChrono(Integer numChrono) {
        this.numChrono = numChrono;
    }

    public Integer getParentFactureDetailRef() {
        return parentFactureDetailRef;
    }

    public void setParentFactureDetailRef(Integer parentFactureDetailRef) {
        this.parentFactureDetailRef = parentFactureDetailRef;
    }

    public Integer getTaxePourcentage() {
        return taxePourcentage;
    }

    public void setTaxePourcentage(Integer taxePourcentage) {
        this.taxePourcentage = taxePourcentage;
    }

    public Integer getTaxeMontant() {
        return taxeMontant;
    }

    public void setTaxeMontant(Integer taxeMontant) {
        this.taxeMontant = taxeMontant;
    }

    public Integer getModePaiementTotal() {
        return modePaiementTotal;
    }

    public void setModePaiementTotal(Integer modePaiementTotal) {
        this.modePaiementTotal = modePaiementTotal;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getTransactionEmployeRef() {
        return transactionEmployeRef;
    }

    public void setTransactionEmployeRef(Integer transactionEmployeRef) {
        this.transactionEmployeRef = transactionEmployeRef;
    }

    public Integer getTransfertEmployeRef() {
        return transfertEmployeRef;
    }

    public void setTransfertEmployeRef(Integer transfertEmployeRef) {
        this.transfertEmployeRef = transfertEmployeRef;
    }

    public Integer getManagerEmployeRef() {
        return managerEmployeRef;
    }

    public void setManagerEmployeRef(Integer managerEmployeRef) {
        this.managerEmployeRef = managerEmployeRef;
    }

    public Integer getRepasEmployeRef() {
        return repasEmployeRef;
    }

    public void setRepasEmployeRef(Integer repasEmployeRef) {
        this.repasEmployeRef = repasEmployeRef;
    }

    public Integer getRemiseRef() {
        return remiseRef;
    }

    public void setRemiseRef(Integer remiseRef) {
        this.remiseRef = remiseRef;
    }

    public Integer getRemiseElementMenuRef() {
        return remiseElementMenuRef;
    }

    public void setRemiseElementMenuRef(Integer remiseElementMenuRef) {
        this.remiseElementMenuRef = remiseElementMenuRef;
    }

    public Integer getCommissionServiceRef() {
        return commissionServiceRef;
    }

    public void setCommissionServiceRef(Integer commissionServiceRef) {
        this.commissionServiceRef = commissionServiceRef;
    }

    public Integer getModePaiementRef() {
        return modePaiementRef;
    }

    public void setModePaiementRef(Integer modePaiementRef) {
        this.modePaiementRef = modePaiementRef;
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
        if (!(o instanceof FactureDetailDTO)) {
            return false;
        }

        FactureDetailDTO factureDetailDTO = (FactureDetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, factureDetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDetailDTO{" +
            "id=" + getId() +
            ", factureEnTeteRef=" + getFactureEnTeteRef() +
            ", numLigne=" + getNumLigne() +
            ", detailType='" + getDetailType() + "'" +
            ", utcDateTime='" + getUtcDateTime() + "'" +
            ", lclDateTime='" + getLclDateTime() + "'" +
            ", numSiege='" + getNumSiege() + "'" +
            ", niveauPrix=" + getNiveauPrix() +
            ", totalAffiche=" + getTotalAffiche() +
            ", quantiteAffiche=" + getQuantiteAffiche() +
            ", estErreur='" + getEstErreur() + "'" +
            ", estNul='" + getEstNul() + "'" +
            ", estRetourne='" + getEstRetourne() + "'" +
            ", estInvisible='" + getEstInvisible() + "'" +
            ", totalLigne=" + getTotalLigne() +
            ", codeRaisonRef=" + getCodeRaisonRef() +
            ", multiplicateur=" + getMultiplicateur() +
            ", referenceInfo='" + getReferenceInfo() + "'" +
            ", referenceInfo2='" + getReferenceInfo2() + "'" +
            ", partieDeJourneeRef=" + getPartieDeJourneeRef() +
            ", periodeDeServiceRef=" + getPeriodeDeServiceRef() +
            ", numChrono=" + getNumChrono() +
            ", parentFactureDetailRef=" + getParentFactureDetailRef() +
            ", taxePourcentage=" + getTaxePourcentage() +
            ", taxeMontant=" + getTaxeMontant() +
            ", modePaiementTotal=" + getModePaiementTotal() +
            ", prix=" + getPrix() +
            ", transactionEmployeRef=" + getTransactionEmployeRef() +
            ", transfertEmployeRef=" + getTransfertEmployeRef() +
            ", managerEmployeRef=" + getManagerEmployeRef() +
            ", repasEmployeRef=" + getRepasEmployeRef() +
            ", remiseRef=" + getRemiseRef() +
            ", remiseElementMenuRef=" + getRemiseElementMenuRef() +
            ", commissionServiceRef=" + getCommissionServiceRef() +
            ", modePaiementRef=" + getModePaiementRef() +
            ", elementMenuRef=" + getElementMenuRef() +
            "}";
    }
}
