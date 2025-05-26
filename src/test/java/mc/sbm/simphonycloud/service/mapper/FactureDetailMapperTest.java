package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.FactureDetailAsserts.*;
import static mc.sbm.simphonycloud.domain.FactureDetailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactureDetailMapperTest {

    private FactureDetailMapper factureDetailMapper;

    @BeforeEach
    void setUp() {
        factureDetailMapper = new FactureDetailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFactureDetailSample1();
        var actual = factureDetailMapper.toEntity(factureDetailMapper.toDto(expected));
        assertFactureDetailAllPropertiesEquals(expected, actual);
    }
}
