package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HierDTO.class);
        HierDTO hierDTO1 = new HierDTO();
        hierDTO1.setId("id1");
        HierDTO hierDTO2 = new HierDTO();
        assertThat(hierDTO1).isNotEqualTo(hierDTO2);
        hierDTO2.setId(hierDTO1.getId());
        assertThat(hierDTO1).isEqualTo(hierDTO2);
        hierDTO2.setId("id2");
        assertThat(hierDTO1).isNotEqualTo(hierDTO2);
        hierDTO1.setId(null);
        assertThat(hierDTO1).isNotEqualTo(hierDTO2);
    }
}
