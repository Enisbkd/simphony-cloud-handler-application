package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartieDeJourneeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartieDeJourneeDTO.class);
        PartieDeJourneeDTO partieDeJourneeDTO1 = new PartieDeJourneeDTO();
        partieDeJourneeDTO1.setId(1L);
        PartieDeJourneeDTO partieDeJourneeDTO2 = new PartieDeJourneeDTO();
        assertThat(partieDeJourneeDTO1).isNotEqualTo(partieDeJourneeDTO2);
        partieDeJourneeDTO2.setId(partieDeJourneeDTO1.getId());
        assertThat(partieDeJourneeDTO1).isEqualTo(partieDeJourneeDTO2);
        partieDeJourneeDTO2.setId(2L);
        assertThat(partieDeJourneeDTO1).isNotEqualTo(partieDeJourneeDTO2);
        partieDeJourneeDTO1.setId(null);
        assertThat(partieDeJourneeDTO1).isNotEqualTo(partieDeJourneeDTO2);
    }
}
