package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureDetailDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureDetailDTO.class);
        FactureDetailDTO factureDetailDTO1 = new FactureDetailDTO();
        factureDetailDTO1.setId(1L);
        FactureDetailDTO factureDetailDTO2 = new FactureDetailDTO();
        assertThat(factureDetailDTO1).isNotEqualTo(factureDetailDTO2);
        factureDetailDTO2.setId(factureDetailDTO1.getId());
        assertThat(factureDetailDTO1).isEqualTo(factureDetailDTO2);
        factureDetailDTO2.setId(2L);
        assertThat(factureDetailDTO1).isNotEqualTo(factureDetailDTO2);
        factureDetailDTO1.setId(null);
        assertThat(factureDetailDTO1).isNotEqualTo(factureDetailDTO2);
    }
}
