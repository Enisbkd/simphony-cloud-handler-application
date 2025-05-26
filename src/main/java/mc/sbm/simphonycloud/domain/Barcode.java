package mc.sbm.simphonycloud.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Barcode.
 */
@Entity
@Table(name = "barcode")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Barcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "num")
    private Integer num;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "prix")
    private Integer prix;

    @Column(name = "cout_preparation")
    private Integer coutPreparation;

    @Column(name = "def_num_sequence")
    private Integer defNumSequence;

    @Column(name = "prix_num_sequence")
    private Integer prixNumSequence;

    @NotNull
    @Column(name = "point_de_vente_ref", nullable = false)
    private Integer pointDeVenteRef;

    @Column(name = "element_menu_ref")
    private Integer elementMenuRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public Barcode id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return this.num;
    }

    public Barcode num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public Barcode barcode(String barcode) {
        this.setBarcode(barcode);
        return this;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getPrix() {
        return this.prix;
    }

    public Barcode prix(Integer prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getCoutPreparation() {
        return this.coutPreparation;
    }

    public Barcode coutPreparation(Integer coutPreparation) {
        this.setCoutPreparation(coutPreparation);
        return this;
    }

    public void setCoutPreparation(Integer coutPreparation) {
        this.coutPreparation = coutPreparation;
    }

    public Integer getDefNumSequence() {
        return this.defNumSequence;
    }

    public Barcode defNumSequence(Integer defNumSequence) {
        this.setDefNumSequence(defNumSequence);
        return this;
    }

    public void setDefNumSequence(Integer defNumSequence) {
        this.defNumSequence = defNumSequence;
    }

    public Integer getPrixNumSequence() {
        return this.prixNumSequence;
    }

    public Barcode prixNumSequence(Integer prixNumSequence) {
        this.setPrixNumSequence(prixNumSequence);
        return this;
    }

    public void setPrixNumSequence(Integer prixNumSequence) {
        this.prixNumSequence = prixNumSequence;
    }

    public Integer getPointDeVenteRef() {
        return this.pointDeVenteRef;
    }

    public Barcode pointDeVenteRef(Integer pointDeVenteRef) {
        this.setPointDeVenteRef(pointDeVenteRef);
        return this;
    }

    public void setPointDeVenteRef(Integer pointDeVenteRef) {
        this.pointDeVenteRef = pointDeVenteRef;
    }

    public Integer getElementMenuRef() {
        return this.elementMenuRef;
    }

    public Barcode elementMenuRef(Integer elementMenuRef) {
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
        if (!(o instanceof Barcode)) {
            return false;
        }
        return getId() != null && getId().equals(((Barcode) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Barcode{" +
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
