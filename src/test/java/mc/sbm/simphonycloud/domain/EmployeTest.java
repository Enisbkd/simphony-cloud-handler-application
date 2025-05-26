package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.EmployeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employe.class);
        Employe employe1 = getEmployeSample1();
        Employe employe2 = new Employe();
        assertThat(employe1).isNotEqualTo(employe2);

        employe2.setId(employe1.getId());
        assertThat(employe1).isEqualTo(employe2);

        employe2 = getEmployeSample2();
        assertThat(employe1).isNotEqualTo(employe2);
    }
}
