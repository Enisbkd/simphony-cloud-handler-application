package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FamilyGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyGroupDTO.class);
        FamilyGroupDTO familyGroupDTO1 = new FamilyGroupDTO();
        familyGroupDTO1.setId(1L);
        FamilyGroupDTO familyGroupDTO2 = new FamilyGroupDTO();
        assertThat(familyGroupDTO1).isNotEqualTo(familyGroupDTO2);
        familyGroupDTO2.setId(familyGroupDTO1.getId());
        assertThat(familyGroupDTO1).isEqualTo(familyGroupDTO2);
        familyGroupDTO2.setId(2L);
        assertThat(familyGroupDTO1).isNotEqualTo(familyGroupDTO2);
        familyGroupDTO1.setId(null);
        assertThat(familyGroupDTO1).isNotEqualTo(familyGroupDTO2);
    }
}
