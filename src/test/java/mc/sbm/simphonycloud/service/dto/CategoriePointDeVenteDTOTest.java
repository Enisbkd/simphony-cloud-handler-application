package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriePointDeVenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriePointDeVenteDTO.class);
        CategoriePointDeVenteDTO categoriePointDeVenteDTO1 = new CategoriePointDeVenteDTO();
        categoriePointDeVenteDTO1.setId(1L);
        CategoriePointDeVenteDTO categoriePointDeVenteDTO2 = new CategoriePointDeVenteDTO();
        assertThat(categoriePointDeVenteDTO1).isNotEqualTo(categoriePointDeVenteDTO2);
        categoriePointDeVenteDTO2.setId(categoriePointDeVenteDTO1.getId());
        assertThat(categoriePointDeVenteDTO1).isEqualTo(categoriePointDeVenteDTO2);
        categoriePointDeVenteDTO2.setId(2L);
        assertThat(categoriePointDeVenteDTO1).isNotEqualTo(categoriePointDeVenteDTO2);
        categoriePointDeVenteDTO1.setId(null);
        assertThat(categoriePointDeVenteDTO1).isNotEqualTo(categoriePointDeVenteDTO2);
    }
}
