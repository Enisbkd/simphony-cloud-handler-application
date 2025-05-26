package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FactureDetail.
 */
@Entity
@Table(name = "facture_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "facture_en_tete_ref")
    private Integer factureEnTeteRef;

    @Column(name = "num_ligne")
    private Integer numLigne;

    @Column(name = "detail_type")
    private String detailType;

    @Column(name = "utc_date_time")
    private Instant utcDateTime;

    @Column(name = "lcl_date_time")
    private Instant lclDateTime;

    @Column(name = "num_siege")
    private String numSiege;

    @Column(name = "niveau_prix")
    private Float niveauPrix;

    @Column(name = "total_affiche")
    private Float totalAffiche;

    @Column(name = "quantite_affiche")
    private Integer quantiteAffiche;

    @Column(name = "est_erreur")
    private Boolean estErreur;

    @Column(name = "est_nul")
    private Boolean estNul;

    @Column(name = "est_retourne")
    private Boolean estRetourne;

    @Column(name = "est_invisible")
    private Boolean estInvisible;

    @Column(name = "total_ligne")
    private Integer totalLigne;

    @Column(name = "code_raison_ref")
    private Integer codeRaisonRef;

    @Column(name = "multiplicateur")
    private Integer multiplicateur;

    @Column(name = "reference_info")
    private String referenceInfo;

    @Column(name = "reference_info_2")
    private String referenceInfo2;

    @Column(name = "partie_de_journee_ref")
    private Integer partieDeJourneeRef;

    @Column(name = "periode_de_service_ref")
    private Integer periodeDeServiceRef;

    @Column(name = "num_chrono")
    private Integer numChrono;

    @Column(name = "parent_facture_detail_ref")
    private Integer parentFactureDetailRef;

    @Column(name = "taxe_pourcentage")
    private Integer taxePourcentage;

    @Column(name = "taxe_montant")
    private Integer taxeMontant;

    @Column(name = "mode_paiement_total")
    private Integer modePaiementTotal;

    @Column(name = "prix")
    private Integer prix;

    @Column(name = "transaction_employe_ref")
    private Integer transactionEmployeRef;

    @Column(name = "transfert_employe_ref")
    private Integer transfertEmployeRef;

    @Column(name = "manager_employe_ref")
    private Integer managerEmployeRef;

    @Column(name = "repas_employe_ref")
    private Integer repasEmployeRef;

    @Column(name = "remise_ref")
    private Integer remiseRef;

    @Column(name = "remise_element_menu_ref")
    private Integer remiseElementMenuRef;

    @Column(name = "commission_service_ref")
    private Integer commissionServiceRef;

    @Column(name = "mode_paiement_ref")
    private Integer modePaiementRef;

    @Column(name = "element_menu_ref")
    private Integer elementMenuRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public FactureDetail id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFactureEnTeteRef() {
        return this.factureEnTeteRef;
    }

    public FactureDetail factureEnTeteRef(Integer factureEnTeteRef) {
        this.setFactureEnTeteRef(factureEnTeteRef);
        return this;
    }

    public void setFactureEnTeteRef(Integer factureEnTeteRef) {
        this.factureEnTeteRef = factureEnTeteRef;
    }

    public Integer getNumLigne() {
        return this.numLigne;
    }

    public FactureDetail numLigne(Integer numLigne) {
        this.setNumLigne(numLigne);
        return this;
    }

    public void setNumLigne(Integer numLigne) {
        this.numLigne = numLigne;
    }

    public String getDetailType() {
        return this.detailType;
    }

    public FactureDetail detailType(String detailType) {
        this.setDetailType(detailType);
        return this;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public Instant getUtcDateTime() {
        return this.utcDateTime;
    }

    public FactureDetail utcDateTime(Instant utcDateTime) {
        this.setUtcDateTime(utcDateTime);
        return this;
    }

    public void setUtcDateTime(Instant utcDateTime) {
        this.utcDateTime = utcDateTime;
    }

    public Instant getLclDateTime() {
        return this.lclDateTime;
    }

    public FactureDetail lclDateTime(Instant lclDateTime) {
        this.setLclDateTime(lclDateTime);
        return this;
    }

    public void setLclDateTime(Instant lclDateTime) {
        this.lclDateTime = lclDateTime;
    }

    public String getNumSiege() {
        return this.numSiege;
    }

    public FactureDetail numSiege(String numSiege) {
        this.setNumSiege(numSiege);
        return this;
    }

    public void setNumSiege(String numSiege) {
        this.numSiege = numSiege;
    }

    public Float getNiveauPrix() {
        return this.niveauPrix;
    }

    public FactureDetail niveauPrix(Float niveauPrix) {
        this.setNiveauPrix(niveauPrix);
        return this;
    }

    public void setNiveauPrix(Float niveauPrix) {
        this.niveauPrix = niveauPrix;
    }

    public Float getTotalAffiche() {
        return this.totalAffiche;
    }

    public FactureDetail totalAffiche(Float totalAffiche) {
        this.setTotalAffiche(totalAffiche);
        return this;
    }

    public void setTotalAffiche(Float totalAffiche) {
        this.totalAffiche = totalAffiche;
    }

    public Integer getQuantiteAffiche() {
        return this.quantiteAffiche;
    }

    public FactureDetail quantiteAffiche(Integer quantiteAffiche) {
        this.setQuantiteAffiche(quantiteAffiche);
        return this;
    }

    public void setQuantiteAffiche(Integer quantiteAffiche) {
        this.quantiteAffiche = quantiteAffiche;
    }

    public Boolean getEstErreur() {
        return this.estErreur;
    }

    public FactureDetail estErreur(Boolean estErreur) {
        this.setEstErreur(estErreur);
        return this;
    }

    public void setEstErreur(Boolean estErreur) {
        this.estErreur = estErreur;
    }

    public Boolean getEstNul() {
        return this.estNul;
    }

    public FactureDetail estNul(Boolean estNul) {
        this.setEstNul(estNul);
        return this;
    }

    public void setEstNul(Boolean estNul) {
        this.estNul = estNul;
    }

    public Boolean getEstRetourne() {
        return this.estRetourne;
    }

    public FactureDetail estRetourne(Boolean estRetourne) {
        this.setEstRetourne(estRetourne);
        return this;
    }

    public void setEstRetourne(Boolean estRetourne) {
        this.estRetourne = estRetourne;
    }

    public Boolean getEstInvisible() {
        return this.estInvisible;
    }

    public FactureDetail estInvisible(Boolean estInvisible) {
        this.setEstInvisible(estInvisible);
        return this;
    }

    public void setEstInvisible(Boolean estInvisible) {
        this.estInvisible = estInvisible;
    }

    public Integer getTotalLigne() {
        return this.totalLigne;
    }

    public FactureDetail totalLigne(Integer totalLigne) {
        this.setTotalLigne(totalLigne);
        return this;
    }

    public void setTotalLigne(Integer totalLigne) {
        this.totalLigne = totalLigne;
    }

    public Integer getCodeRaisonRef() {
        return this.codeRaisonRef;
    }

    public FactureDetail codeRaisonRef(Integer codeRaisonRef) {
        this.setCodeRaisonRef(codeRaisonRef);
        return this;
    }

    public void setCodeRaisonRef(Integer codeRaisonRef) {
        this.codeRaisonRef = codeRaisonRef;
    }

    public Integer getMultiplicateur() {
        return this.multiplicateur;
    }

    public FactureDetail multiplicateur(Integer multiplicateur) {
        this.setMultiplicateur(multiplicateur);
        return this;
    }

    public void setMultiplicateur(Integer multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    public String getReferenceInfo() {
        return this.referenceInfo;
    }

    public FactureDetail referenceInfo(String referenceInfo) {
        this.setReferenceInfo(referenceInfo);
        return this;
    }

    public void setReferenceInfo(String referenceInfo) {
        this.referenceInfo = referenceInfo;
    }

    public String getReferenceInfo2() {
        return this.referenceInfo2;
    }

    public FactureDetail referenceInfo2(String referenceInfo2) {
        this.setReferenceInfo2(referenceInfo2);
        return this;
    }

    public void setReferenceInfo2(String referenceInfo2) {
        this.referenceInfo2 = referenceInfo2;
    }

    public Integer getPartieDeJourneeRef() {
        return this.partieDeJourneeRef;
    }

    public FactureDetail partieDeJourneeRef(Integer partieDeJourneeRef) {
        this.setPartieDeJourneeRef(partieDeJourneeRef);
        return this;
    }

    public void setPartieDeJourneeRef(Integer partieDeJourneeRef) {
        this.partieDeJourneeRef = partieDeJourneeRef;
    }

    public Integer getPeriodeDeServiceRef() {
        return this.periodeDeServiceRef;
    }

    public FactureDetail periodeDeServiceRef(Integer periodeDeServiceRef) {
        this.setPeriodeDeServiceRef(periodeDeServiceRef);
        return this;
    }

    public void setPeriodeDeServiceRef(Integer periodeDeServiceRef) {
        this.periodeDeServiceRef = periodeDeServiceRef;
    }

    public Integer getNumChrono() {
        return this.numChrono;
    }

    public FactureDetail numChrono(Integer numChrono) {
        this.setNumChrono(numChrono);
        return this;
    }

    public void setNumChrono(Integer numChrono) {
        this.numChrono = numChrono;
    }

    public Integer getParentFactureDetailRef() {
        return this.parentFactureDetailRef;
    }

    public FactureDetail parentFactureDetailRef(Integer parentFactureDetailRef) {
        this.setParentFactureDetailRef(parentFactureDetailRef);
        return this;
    }

    public void setParentFactureDetailRef(Integer parentFactureDetailRef) {
        this.parentFactureDetailRef = parentFactureDetailRef;
    }

    public Integer getTaxePourcentage() {
        return this.taxePourcentage;
    }

    public FactureDetail taxePourcentage(Integer taxePourcentage) {
        this.setTaxePourcentage(taxePourcentage);
        return this;
    }

    public void setTaxePourcentage(Integer taxePourcentage) {
        this.taxePourcentage = taxePourcentage;
    }

    public Integer getTaxeMontant() {
        return this.taxeMontant;
    }

    public FactureDetail taxeMontant(Integer taxeMontant) {
        this.setTaxeMontant(taxeMontant);
        return this;
    }

    public void setTaxeMontant(Integer taxeMontant) {
        this.taxeMontant = taxeMontant;
    }

    public Integer getModePaiementTotal() {
        return this.modePaiementTotal;
    }

    public FactureDetail modePaiementTotal(Integer modePaiementTotal) {
        this.setModePaiementTotal(modePaiementTotal);
        return this;
    }

    public void setModePaiementTotal(Integer modePaiementTotal) {
        this.modePaiementTotal = modePaiementTotal;
    }

    public Integer getPrix() {
        return this.prix;
    }

    public FactureDetail prix(Integer prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getTransactionEmployeRef() {
        return this.transactionEmployeRef;
    }

    public FactureDetail transactionEmployeRef(Integer transactionEmployeRef) {
        this.setTransactionEmployeRef(transactionEmployeRef);
        return this;
    }

    public void setTransactionEmployeRef(Integer transactionEmployeRef) {
        this.transactionEmployeRef = transactionEmployeRef;
    }

    public Integer getTransfertEmployeRef() {
        return this.transfertEmployeRef;
    }

    public FactureDetail transfertEmployeRef(Integer transfertEmployeRef) {
        this.setTransfertEmployeRef(transfertEmployeRef);
        return this;
    }

    public void setTransfertEmployeRef(Integer transfertEmployeRef) {
        this.transfertEmployeRef = transfertEmployeRef;
    }

    public Integer getManagerEmployeRef() {
        return this.managerEmployeRef;
    }

    public FactureDetail managerEmployeRef(Integer managerEmployeRef) {
        this.setManagerEmployeRef(managerEmployeRef);
        return this;
    }

    public void setManagerEmployeRef(Integer managerEmployeRef) {
        this.managerEmployeRef = managerEmployeRef;
    }

    public Integer getRepasEmployeRef() {
        return this.repasEmployeRef;
    }

    public FactureDetail repasEmployeRef(Integer repasEmployeRef) {
        this.setRepasEmployeRef(repasEmployeRef);
        return this;
    }

    public void setRepasEmployeRef(Integer repasEmployeRef) {
        this.repasEmployeRef = repasEmployeRef;
    }

    public Integer getRemiseRef() {
        return this.remiseRef;
    }

    public FactureDetail remiseRef(Integer remiseRef) {
        this.setRemiseRef(remiseRef);
        return this;
    }

    public void setRemiseRef(Integer remiseRef) {
        this.remiseRef = remiseRef;
    }

    public Integer getRemiseElementMenuRef() {
        return this.remiseElementMenuRef;
    }

    public FactureDetail remiseElementMenuRef(Integer remiseElementMenuRef) {
        this.setRemiseElementMenuRef(remiseElementMenuRef);
        return this;
    }

    public void setRemiseElementMenuRef(Integer remiseElementMenuRef) {
        this.remiseElementMenuRef = remiseElementMenuRef;
    }

    public Integer getCommissionServiceRef() {
        return this.commissionServiceRef;
    }

    public FactureDetail commissionServiceRef(Integer commissionServiceRef) {
        this.setCommissionServiceRef(commissionServiceRef);
        return this;
    }

    public void setCommissionServiceRef(Integer commissionServiceRef) {
        this.commissionServiceRef = commissionServiceRef;
    }

    public Integer getModePaiementRef() {
        return this.modePaiementRef;
    }

    public FactureDetail modePaiementRef(Integer modePaiementRef) {
        this.setModePaiementRef(modePaiementRef);
        return this;
    }

    public void setModePaiementRef(Integer modePaiementRef) {
        this.modePaiementRef = modePaiementRef;
    }

    public Integer getElementMenuRef() {
        return this.elementMenuRef;
    }

    public FactureDetail elementMenuRef(Integer elementMenuRef) {
        this.setElementMenuRef(elementMenuRef);
        return this;
    }

    public void setElementMenuRef(Integer elementMenuRef) {
        this.elementMenuRef = elementMenuRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureDetail)) {
            return false;
        }
        return getId() != null && getId().equals(((FactureDetail) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureDetail{" +
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
