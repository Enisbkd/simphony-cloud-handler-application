package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommissionServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionServiceDTO.class);
        CommissionServiceDTO commissionServiceDTO1 = new CommissionServiceDTO();
        commissionServiceDTO1.setId(1L);
        CommissionServiceDTO commissionServiceDTO2 = new CommissionServiceDTO();
        assertThat(commissionServiceDTO1).isNotEqualTo(commissionServiceDTO2);
        commissionServiceDTO2.setId(commissionServiceDTO1.getId());
        assertThat(commissionServiceDTO1).isEqualTo(commissionServiceDTO2);
        commissionServiceDTO2.setId(2L);
        assertThat(commissionServiceDTO1).isNotEqualTo(commissionServiceDTO2);
        commissionServiceDTO1.setId(null);
        assertThat(commissionServiceDTO1).isNotEqualTo(commissionServiceDTO2);
    }
}
