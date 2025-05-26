package mc.sbm.simphonycloud.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mc.sbm.simphonycloud.domain.Employe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeDTO implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer num;

    private String firstName;

    private String lastName;

    private String userName;

    @NotNull
    private String etablissementRef;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        if (!(o instanceof EmployeDTO)) {
            return false;
        }

        EmployeDTO employeDTO = (EmployeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeDTO{" +
            "id=" + getId() +
            ", num=" + getNum() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", etablissementRef='" + getEtablissementRef() + "'" +
            "}";
    }
}
