package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.PointDeVenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointDeVenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointDeVente.class);
        PointDeVente pointDeVente1 = getPointDeVenteSample1();
        PointDeVente pointDeVente2 = new PointDeVente();
        assertThat(pointDeVente1).isNotEqualTo(pointDeVente2);

        pointDeVente2.setId(pointDeVente1.getId());
        assertThat(pointDeVente1).isEqualTo(pointDeVente2);

        pointDeVente2 = getPointDeVenteSample2();
        assertThat(pointDeVente1).isNotEqualTo(pointDeVente2);
    }
}
