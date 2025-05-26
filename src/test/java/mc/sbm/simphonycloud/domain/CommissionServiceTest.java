package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CommissionServiceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommissionServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionService.class);
        CommissionService commissionService1 = getCommissionServiceSample1();
        CommissionService commissionService2 = new CommissionService();
        assertThat(commissionService1).isNotEqualTo(commissionService2);

        commissionService2.setId(commissionService1.getId());
        assertThat(commissionService1).isEqualTo(commissionService2);

        commissionService2 = getCommissionServiceSample2();
        assertThat(commissionService1).isNotEqualTo(commissionService2);
    }
}
