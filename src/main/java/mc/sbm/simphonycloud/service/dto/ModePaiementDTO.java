package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.ModePaiement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModePaiementDTO implements Serializable {

    @NotNull
    private Integer id;

    private String nom;

    private String nomCourt;

    private String nomMstr;

    private String type;

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

    public String getNomMstr() {
        return nomMstr;
    }

    public void setNomMstr(String nomMstr) {
        this.nomMstr = nomMstr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(o instanceof ModePaiementDTO)) {
            return false;
        }

        ModePaiementDTO modePaiementDTO = (ModePaiementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modePaiementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModePaiementDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomCourt='" + getNomCourt() + "'" +
            ", nomMstr='" + getNomMstr() + "'" +
            ", type='" + getType() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
