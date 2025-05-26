package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CodeRaisonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeRaisonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodeRaison.class);
        CodeRaison codeRaison1 = getCodeRaisonSample1();
        CodeRaison codeRaison2 = new CodeRaison();
        assertThat(codeRaison1).isNotEqualTo(codeRaison2);

        codeRaison2.setId(codeRaison1.getId());
        assertThat(codeRaison1).isEqualTo(codeRaison2);

        codeRaison2 = getCodeRaisonSample2();
        assertThat(codeRaison1).isNotEqualTo(codeRaison2);
    }
}
