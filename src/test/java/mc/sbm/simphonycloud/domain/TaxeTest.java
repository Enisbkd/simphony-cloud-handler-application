package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.TaxeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taxe.class);
        Taxe taxe1 = getTaxeSample1();
        Taxe taxe2 = new Taxe();
        assertThat(taxe1).isNotEqualTo(taxe2);

        taxe2.setId(taxe1.getId());
        assertThat(taxe1).isEqualTo(taxe2);

        taxe2 = getTaxeSample2();
        assertThat(taxe1).isNotEqualTo(taxe2);
    }
}
