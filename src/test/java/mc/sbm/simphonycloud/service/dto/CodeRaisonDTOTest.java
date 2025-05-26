package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeRaisonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeRaisonDTO.class);
        CodeRaisonDTO codeRaisonDTO1 = new CodeRaisonDTO();
        codeRaisonDTO1.setId(1L);
        CodeRaisonDTO codeRaisonDTO2 = new CodeRaisonDTO();
        assertThat(codeRaisonDTO1).isNotEqualTo(codeRaisonDTO2);
        codeRaisonDTO2.setId(codeRaisonDTO1.getId());
        assertThat(codeRaisonDTO1).isEqualTo(codeRaisonDTO2);
        codeRaisonDTO2.setId(2L);
        assertThat(codeRaisonDTO1).isNotEqualTo(codeRaisonDTO2);
        codeRaisonDTO1.setId(null);
        assertThat(codeRaisonDTO1).isNotEqualTo(codeRaisonDTO2);
    }
}
