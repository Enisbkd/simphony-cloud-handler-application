package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.FamilyGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FamilyGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyGroup.class);
        FamilyGroup familyGroup1 = getFamilyGroupSample1();
        FamilyGroup familyGroup2 = new FamilyGroup();
        assertThat(familyGroup1).isNotEqualTo(familyGroup2);

        familyGroup2.setId(familyGroup1.getId());
        assertThat(familyGroup1).isEqualTo(familyGroup2);

        familyGroup2 = getFamilyGroupSample2();
        assertThat(familyGroup1).isNotEqualTo(familyGroup2);
    }
}
