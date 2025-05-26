package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.CategoriePointDeVenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriePointDeVenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriePointDeVente.class);
        CategoriePointDeVente categoriePointDeVente1 = getCategoriePointDeVenteSample1();
        CategoriePointDeVente categoriePointDeVente2 = new CategoriePointDeVente();
        assertThat(categoriePointDeVente1).isNotEqualTo(categoriePointDeVente2);

        categoriePointDeVente2.setId(categoriePointDeVente1.getId());
        assertThat(categoriePointDeVente1).isEqualTo(categoriePointDeVente2);

        categoriePointDeVente2 = getCategoriePointDeVenteSample2();
        assertThat(categoriePointDeVente1).isNotEqualTo(categoriePointDeVente2);
    }
}
