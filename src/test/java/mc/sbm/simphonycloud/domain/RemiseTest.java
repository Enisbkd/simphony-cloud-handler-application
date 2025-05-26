package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.RemiseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemiseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remise.class);
        Remise remise1 = getRemiseSample1();
        Remise remise2 = new Remise();
        assertThat(remise1).isNotEqualTo(remise2);

        remise2.setId(remise1.getId());
        assertThat(remise1).isEqualTo(remise2);

        remise2 = getRemiseSample2();
        assertThat(remise1).isNotEqualTo(remise2);
    }
}
