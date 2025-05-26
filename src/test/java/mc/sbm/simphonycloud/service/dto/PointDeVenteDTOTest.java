package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PointDeVenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointDeVenteDTO.class);
        PointDeVenteDTO pointDeVenteDTO1 = new PointDeVenteDTO();
        pointDeVenteDTO1.setId(1L);
        PointDeVenteDTO pointDeVenteDTO2 = new PointDeVenteDTO();
        assertThat(pointDeVenteDTO1).isNotEqualTo(pointDeVenteDTO2);
        pointDeVenteDTO2.setId(pointDeVenteDTO1.getId());
        assertThat(pointDeVenteDTO1).isEqualTo(pointDeVenteDTO2);
        pointDeVenteDTO2.setId(2L);
        assertThat(pointDeVenteDTO1).isNotEqualTo(pointDeVenteDTO2);
        pointDeVenteDTO1.setId(null);
        assertThat(pointDeVenteDTO1).isNotEqualTo(pointDeVenteDTO2);
    }
}
