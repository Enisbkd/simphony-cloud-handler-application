package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.FactureEnTeteAsserts.*;
import static mc.sbm.simphonycloud.domain.FactureEnTeteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactureEnTeteMapperTest {

    private FactureEnTeteMapper factureEnTeteMapper;

    @BeforeEach
    void setUp() {
        factureEnTeteMapper = new FactureEnTeteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFactureEnTeteSample1();
        var actual = factureEnTeteMapper.toEntity(factureEnTeteMapper.toDto(expected));
        assertFactureEnTeteAllPropertiesEquals(expected, actual);
    }
}
