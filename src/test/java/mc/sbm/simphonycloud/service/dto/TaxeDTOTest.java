package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaxeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxeDTO.class);
        TaxeDTO taxeDTO1 = new TaxeDTO();
        taxeDTO1.setId(1L);
        TaxeDTO taxeDTO2 = new TaxeDTO();
        assertThat(taxeDTO1).isNotEqualTo(taxeDTO2);
        taxeDTO2.setId(taxeDTO1.getId());
        assertThat(taxeDTO1).isEqualTo(taxeDTO2);
        taxeDTO2.setId(2L);
        assertThat(taxeDTO1).isNotEqualTo(taxeDTO2);
        taxeDTO1.setId(null);
        assertThat(taxeDTO1).isNotEqualTo(taxeDTO2);
    }
}
