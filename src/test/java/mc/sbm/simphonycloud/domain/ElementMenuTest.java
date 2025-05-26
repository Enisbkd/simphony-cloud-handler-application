package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.ElementMenuTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElementMenuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementMenu.class);
        ElementMenu elementMenu1 = getElementMenuSample1();
        ElementMenu elementMenu2 = new ElementMenu();
        assertThat(elementMenu1).isNotEqualTo(elementMenu2);

        elementMenu2.setId(elementMenu1.getId());
        assertThat(elementMenu1).isEqualTo(elementMenu2);

        elementMenu2 = getElementMenuSample2();
        assertThat(elementMenu1).isNotEqualTo(elementMenu2);
    }
}
