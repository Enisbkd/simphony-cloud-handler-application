package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemiseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemiseDTO.class);
        RemiseDTO remiseDTO1 = new RemiseDTO();
        remiseDTO1.setId(1L);
        RemiseDTO remiseDTO2 = new RemiseDTO();
        assertThat(remiseDTO1).isNotEqualTo(remiseDTO2);
        remiseDTO2.setId(remiseDTO1.getId());
        assertThat(remiseDTO1).isEqualTo(remiseDTO2);
        remiseDTO2.setId(2L);
        assertThat(remiseDTO1).isNotEqualTo(remiseDTO2);
        remiseDTO1.setId(null);
        assertThat(remiseDTO1).isNotEqualTo(remiseDTO2);
    }
}
