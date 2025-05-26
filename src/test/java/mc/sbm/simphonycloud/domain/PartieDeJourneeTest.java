package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.PartieDeJourneeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartieDeJourneeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartieDeJournee.class);
        PartieDeJournee partieDeJournee1 = getPartieDeJourneeSample1();
        PartieDeJournee partieDeJournee2 = new PartieDeJournee();
        assertThat(partieDeJournee1).isNotEqualTo(partieDeJournee2);

        partieDeJournee2.setId(partieDeJournee1.getId());
        assertThat(partieDeJournee1).isEqualTo(partieDeJournee2);

        partieDeJournee2 = getPartieDeJourneeSample2();
        assertThat(partieDeJournee1).isNotEqualTo(partieDeJournee2);
    }
}
