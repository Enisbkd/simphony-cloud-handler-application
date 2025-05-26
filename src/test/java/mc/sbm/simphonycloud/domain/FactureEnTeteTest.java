package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.FactureEnTeteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureEnTeteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureEnTete.class);
        FactureEnTete factureEnTete1 = getFactureEnTeteSample1();
        FactureEnTete factureEnTete2 = new FactureEnTete();
        assertThat(factureEnTete1).isNotEqualTo(factureEnTete2);

        factureEnTete2.setId(factureEnTete1.getId());
        assertThat(factureEnTete1).isEqualTo(factureEnTete2);

        factureEnTete2 = getFactureEnTeteSample2();
        assertThat(factureEnTete1).isNotEqualTo(factureEnTete2);
    }
}
