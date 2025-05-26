package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MajorGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MajorGroupDTO.class);
        MajorGroupDTO majorGroupDTO1 = new MajorGroupDTO();
        majorGroupDTO1.setId(1L);
        MajorGroupDTO majorGroupDTO2 = new MajorGroupDTO();
        assertThat(majorGroupDTO1).isNotEqualTo(majorGroupDTO2);
        majorGroupDTO2.setId(majorGroupDTO1.getId());
        assertThat(majorGroupDTO1).isEqualTo(majorGroupDTO2);
        majorGroupDTO2.setId(2L);
        assertThat(majorGroupDTO1).isNotEqualTo(majorGroupDTO2);
        majorGroupDTO1.setId(null);
        assertThat(majorGroupDTO1).isNotEqualTo(majorGroupDTO2);
    }
}
