package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Etablissement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EtablissementDTO implements Serializable {

    @NotNull
    private String id;

    @NotNull
    private String nom;

    private Boolean estGroupe;

    private String sourceVersion;

    @NotNull
    private String societeRef;

    @NotNull
    private String hierRef;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getEstGroupe() {
        return estGroupe;
    }

    public void setEstGroupe(Boolean estGroupe) {
        this.estGroupe = estGroupe;
    }

    public String getSourceVersion() {
        return sourceVersion;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public String getSocieteRef() {
        return societeRef;
    }

    public void setSocieteRef(String societeRef) {
        this.societeRef = societeRef;
    }

    public String getHierRef() {
        return hierRef;
    }

    public void setHierRef(String hierRef) {
        this.hierRef = hierRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtablissementDTO)) {
            return false;
        }

        EtablissementDTO etablissementDTO = (EtablissementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, etablissementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtablissementDTO{" +
            "id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", estGroupe='" + getEstGroupe() + "'" +
            ", sourceVersion='" + getSourceVersion() + "'" +
            ", societeRef='" + getSocieteRef() + "'" +
            ", hierRef='" + getHierRef() + "'" +
            "}";
    }
}
