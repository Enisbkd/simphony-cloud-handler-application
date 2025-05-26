package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.CodeRaison} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodeRaisonDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer nomCourt;

    private String nomMstr;

    private Integer numMstr;

    private String name;

    @NotNull
    private String etablissementRef;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNomCourt() {
        return nomCourt;
    }

    public void setNomCourt(Integer nomCourt) {
        this.nomCourt = nomCourt;
    }

    public String getNomMstr() {
        return nomMstr;
    }

    public void setNomMstr(String nomMstr) {
        this.nomMstr = nomMstr;
    }

    public Integer getNumMstr() {
        return numMstr;
    }

    public void setNumMstr(Integer numMstr) {
        this.numMstr = numMstr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof CodeRaisonDTO)) {
            return false;
        }

        CodeRaisonDTO codeRaisonDTO = (CodeRaisonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, codeRaisonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodeRaisonDTO{" +
            "id=" + getId() +
            ", nomCourt=" + getNomCourt() +
            ", nomMstr='" + getNomMstr() + "'" +
            ", numMstr=" + getNumMstr() +
            ", name='" + getName() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
