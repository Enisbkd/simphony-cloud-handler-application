package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.MajorGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MajorGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MajorGroup.class);
        MajorGroup majorGroup1 = getMajorGroupSample1();
        MajorGroup majorGroup2 = new MajorGroup();
        assertThat(majorGroup1).isNotEqualTo(majorGroup2);

        majorGroup2.setId(majorGroup1.getId());
        assertThat(majorGroup1).isEqualTo(majorGroup2);

        majorGroup2 = getMajorGroupSample2();
        assertThat(majorGroup1).isNotEqualTo(majorGroup2);
    }
}
