package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElementMenuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementMenuDTO.class);
        ElementMenuDTO elementMenuDTO1 = new ElementMenuDTO();
        elementMenuDTO1.setId(1L);
        ElementMenuDTO elementMenuDTO2 = new ElementMenuDTO();
        assertThat(elementMenuDTO1).isNotEqualTo(elementMenuDTO2);
        elementMenuDTO2.setId(elementMenuDTO1.getId());
        assertThat(elementMenuDTO1).isEqualTo(elementMenuDTO2);
        elementMenuDTO2.setId(2L);
        assertThat(elementMenuDTO1).isNotEqualTo(elementMenuDTO2);
        elementMenuDTO1.setId(null);
        assertThat(elementMenuDTO1).isNotEqualTo(elementMenuDTO2);
    }
}
