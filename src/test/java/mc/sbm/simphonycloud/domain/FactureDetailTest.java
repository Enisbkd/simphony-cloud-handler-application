package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.FactureDetailTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureDetailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureDetail.class);
        FactureDetail factureDetail1 = getFactureDetailSample1();
        FactureDetail factureDetail2 = new FactureDetail();
        assertThat(factureDetail1).isNotEqualTo(factureDetail2);

        factureDetail2.setId(factureDetail1.getId());
        assertThat(factureDetail1).isEqualTo(factureDetail2);

        factureDetail2 = getFactureDetailSample2();
        assertThat(factureDetail1).isNotEqualTo(factureDetail2);
    }
}
