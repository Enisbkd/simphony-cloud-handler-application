package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureEnTeteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureEnTeteDTO.class);
        FactureEnTeteDTO factureEnTeteDTO1 = new FactureEnTeteDTO();
        factureEnTeteDTO1.setId(1L);
        FactureEnTeteDTO factureEnTeteDTO2 = new FactureEnTeteDTO();
        assertThat(factureEnTeteDTO1).isNotEqualTo(factureEnTeteDTO2);
        factureEnTeteDTO2.setId(factureEnTeteDTO1.getId());
        assertThat(factureEnTeteDTO1).isEqualTo(factureEnTeteDTO2);
        factureEnTeteDTO2.setId(2L);
        assertThat(factureEnTeteDTO1).isNotEqualTo(factureEnTeteDTO2);
        factureEnTeteDTO1.setId(null);
        assertThat(factureEnTeteDTO1).isNotEqualTo(factureEnTeteDTO2);
    }
}
