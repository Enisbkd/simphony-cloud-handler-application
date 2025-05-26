package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FactureEnTete.
 */
@Entity
@Table(name = "facture_en_tete")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FactureEnTete implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "num")
    private Integer num;

    @Column(name = "facture_ref")
    private String factureRef;

    @Column(name = "ouverture_date_time")
    private ZonedDateTime ouvertureDateTime;

    @Column(name = "fermeture_date_time")
    private ZonedDateTime fermetureDateTime;

    @Column(name = "est_annule")
    private Boolean estAnnule;

    @Column(name = "nbre_pax")
    private Integer nbrePax;

    @Column(name = "num_table")
    private Integer numTable;

    @Column(name = "taxe_montant_total")
    private Integer taxeMontantTotal;

    @Column(name = "sous_total")
    private Integer sousTotal;

    @Column(name = "facture_total")
    private Integer factureTotal;

    @Column(name = "commission_total")
    private Integer commissionTotal;

    @Column(name = "tip_total")
    private Integer tipTotal;

    @Column(name = "remise_total")
    private Integer remiseTotal;

    @Column(name = "erreurs_corrigees_total")
    private Integer erreursCorrigeesTotal;

    @Column(name = "retour_total")
    private Integer retourTotal;

    @Column(name = "xfer_to_facture_en_tete_ref")
    private Integer xferToFactureEnTeteRef;

    @Column(name = "xfer_status")
    private String xferStatus;

    @Column(name = "categorie_point_de_vente_ref")
    private Integer categoriePointDeVenteRef;

    @Column(name = "point_de_vente_ref")
    private Integer pointDeVenteRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public FactureEnTete id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return this.num;
    }

    public FactureEnTete num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getFactureRef() {
        return this.factureRef;
    }

    public FactureEnTete factureRef(String factureRef) {
        this.setFactureRef(factureRef);
        return this;
    }

    public void setFactureRef(String factureRef) {
        this.factureRef = factureRef;
    }

    public ZonedDateTime getOuvertureDateTime() {
        return this.ouvertureDateTime;
    }

    public FactureEnTete ouvertureDateTime(ZonedDateTime ouvertureDateTime) {
        this.setOuvertureDateTime(ouvertureDateTime);
        return this;
    }

    public void setOuvertureDateTime(ZonedDateTime ouvertureDateTime) {
        this.ouvertureDateTime = ouvertureDateTime;
    }

    public ZonedDateTime getFermetureDateTime() {
        return this.fermetureDateTime;
    }

    public FactureEnTete fermetureDateTime(ZonedDateTime fermetureDateTime) {
        this.setFermetureDateTime(fermetureDateTime);
        return this;
    }

    public void setFermetureDateTime(ZonedDateTime fermetureDateTime) {
        this.fermetureDateTime = fermetureDateTime;
    }

    public Boolean getEstAnnule() {
        return this.estAnnule;
    }

    public FactureEnTete estAnnule(Boolean estAnnule) {
        this.setEstAnnule(estAnnule);
        return this;
    }

    public void setEstAnnule(Boolean estAnnule) {
        this.estAnnule = estAnnule;
    }

    public Integer getNbrePax() {
        return this.nbrePax;
    }

    public FactureEnTete nbrePax(Integer nbrePax) {
        this.setNbrePax(nbrePax);
        return this;
    }

    public void setNbrePax(Integer nbrePax) {
        this.nbrePax = nbrePax;
    }

    public Integer getNumTable() {
        return this.numTable;
    }

    public FactureEnTete numTable(Integer numTable) {
        this.setNumTable(numTable);
        return this;
    }

    public void setNumTable(Integer numTable) {
        this.numTable = numTable;
    }

    public Integer getTaxeMontantTotal() {
        return this.taxeMontantTotal;
    }

    public FactureEnTete taxeMontantTotal(Integer taxeMontantTotal) {
        this.setTaxeMontantTotal(taxeMontantTotal);
        return this;
    }

    public void setTaxeMontantTotal(Integer taxeMontantTotal) {
        this.taxeMontantTotal = taxeMontantTotal;
    }

    public Integer getSousTotal() {
        return this.sousTotal;
    }

    public FactureEnTete sousTotal(Integer sousTotal) {
        this.setSousTotal(sousTotal);
        return this;
    }

    public void setSousTotal(Integer sousTotal) {
        this.sousTotal = sousTotal;
    }

    public Integer getFactureTotal() {
        return this.factureTotal;
    }

    public FactureEnTete factureTotal(Integer factureTotal) {
        this.setFactureTotal(factureTotal);
        return this;
    }

    public void setFactureTotal(Integer factureTotal) {
        this.factureTotal = factureTotal;
    }

    public Integer getCommissionTotal() {
        return this.commissionTotal;
    }

    public FactureEnTete commissionTotal(Integer commissionTotal) {
        this.setCommissionTotal(commissionTotal);
        return this;
    }

    public void setCommissionTotal(Integer commissionTotal) {
        this.commissionTotal = commissionTotal;
    }

    public Integer getTipTotal() {
        return this.tipTotal;
    }

    public FactureEnTete tipTotal(Integer tipTotal) {
        this.setTipTotal(tipTotal);
        return this;
    }

    public void setTipTotal(Integer tipTotal) {
        this.tipTotal = tipTotal;
    }

    public Integer getRemiseTotal() {
        return this.remiseTotal;
    }

    public FactureEnTete remiseTotal(Integer remiseTotal) {
        this.setRemiseTotal(remiseTotal);
        return this;
    }

    public void setRemiseTotal(Integer remiseTotal) {
        this.remiseTotal = remiseTotal;
    }

    public Integer getErreursCorrigeesTotal() {
        return this.erreursCorrigeesTotal;
    }

    public FactureEnTete erreursCorrigeesTotal(Integer erreursCorrigeesTotal) {
        this.setErreursCorrigeesTotal(erreursCorrigeesTotal);
        return this;
    }

    public void setErreursCorrigeesTotal(Integer erreursCorrigeesTotal) {
        this.erreursCorrigeesTotal = erreursCorrigeesTotal;
    }

    public Integer getRetourTotal() {
        return this.retourTotal;
    }

    public FactureEnTete retourTotal(Integer retourTotal) {
        this.setRetourTotal(retourTotal);
        return this;
    }

    public void setRetourTotal(Integer retourTotal) {
        this.retourTotal = retourTotal;
    }

    public Integer getXferToFactureEnTeteRef() {
        return this.xferToFactureEnTeteRef;
    }

    public FactureEnTete xferToFactureEnTeteRef(Integer xferToFactureEnTeteRef) {
        this.setXferToFactureEnTeteRef(xferToFactureEnTeteRef);
        return this;
    }

    public void setXferToFactureEnTeteRef(Integer xferToFactureEnTeteRef) {
        this.xferToFactureEnTeteRef = xferToFactureEnTeteRef;
    }

    public String getXferStatus() {
        return this.xferStatus;
    }

    public FactureEnTete xferStatus(String xferStatus) {
        this.setXferStatus(xferStatus);
        return this;
    }

    public void setXferStatus(String xferStatus) {
        this.xferStatus = xferStatus;
    }

    public Integer getCategoriePointDeVenteRef() {
        return this.categoriePointDeVenteRef;
    }

    public FactureEnTete categoriePointDeVenteRef(Integer categoriePointDeVenteRef) {
        this.setCategoriePointDeVenteRef(categoriePointDeVenteRef);
        return this;
    }

    public void setCategoriePointDeVenteRef(Integer categoriePointDeVenteRef) {
        this.categoriePointDeVenteRef = categoriePointDeVenteRef;
    }

    public Integer getPointDeVenteRef() {
        return this.pointDeVenteRef;
    }

    public FactureEnTete pointDeVenteRef(Integer pointDeVenteRef) {
        this.setPointDeVenteRef(pointDeVenteRef);
        return this;
    }

    public void setPointDeVenteRef(Integer pointDeVenteRef) {
        this.pointDeVenteRef = pointDeVenteRef;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureEnTete)) {
            return false;
        }
        return getId() != null && getId().equals(((FactureEnTete) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureEnTete{" +
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
