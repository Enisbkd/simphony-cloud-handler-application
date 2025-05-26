package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.SocieteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocieteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Societe.class);
        Societe societe1 = getSocieteSample1();
        Societe societe2 = new Societe();
        assertThat(societe1).isNotEqualTo(societe2);

        societe2.setId(societe1.getId());
        assertThat(societe1).isEqualTo(societe2);

        societe2 = getSocieteSample2();
        assertThat(societe1).isNotEqualTo(societe2);
    }
}
