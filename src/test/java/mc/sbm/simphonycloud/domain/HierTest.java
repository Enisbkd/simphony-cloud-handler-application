package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.HierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hier.class);
        Hier hier1 = getHierSample1();
        Hier hier2 = new Hier();
        assertThat(hier1).isNotEqualTo(hier2);

        hier2.setId(hier1.getId());
        assertThat(hier1).isEqualTo(hier2);

        hier2 = getHierSample2();
        assertThat(hier1).isNotEqualTo(hier2);
    }
}
