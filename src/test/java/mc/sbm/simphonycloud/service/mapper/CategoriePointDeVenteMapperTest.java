package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.CategoriePointDeVenteAsserts.*;
import static mc.sbm.simphonycloud.domain.CategoriePointDeVenteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriePointDeVenteMapperTest {

    private CategoriePointDeVenteMapper categoriePointDeVenteMapper;

    @BeforeEach
    void setUp() {
        categoriePointDeVenteMapper = new CategoriePointDeVenteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCategoriePointDeVenteSample1();
        var actual = categoriePointDeVenteMapper.toEntity(categoriePointDeVenteMapper.toDto(expected));
        assertCategoriePointDeVenteAllPropertiesEquals(expected, actual);
    }
}
