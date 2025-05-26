package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.ModePaiementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModePaiementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModePaiement.class);
        ModePaiement modePaiement1 = getModePaiementSample1();
        ModePaiement modePaiement2 = new ModePaiement();
        assertThat(modePaiement1).isNotEqualTo(modePaiement2);

        modePaiement2.setId(modePaiement1.getId());
        assertThat(modePaiement1).isEqualTo(modePaiement2);

        modePaiement2 = getModePaiementSample2();
        assertThat(modePaiement1).isNotEqualTo(modePaiement2);
    }
}
