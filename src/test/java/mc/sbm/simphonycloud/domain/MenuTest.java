package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.MenuTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = getMenuSample1();
        Menu menu2 = new Menu();
        assertThat(menu1).isNotEqualTo(menu2);

        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);

        menu2 = getMenuSample2();
        assertThat(menu1).isNotEqualTo(menu2);
    }
}
