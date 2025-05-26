package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.PointDeVenteAsserts.*;
import static mc.sbm.simphonycloud.domain.PointDeVenteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointDeVenteMapperTest {

    private PointDeVenteMapper pointDeVenteMapper;

    @BeforeEach
    void setUp() {
        pointDeVenteMapper = new PointDeVenteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPointDeVenteSample1();
        var actual = pointDeVenteMapper.toEntity(pointDeVenteMapper.toDto(expected));
        assertPointDeVenteAllPropertiesEquals(expected, actual);
    }
}
