package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.SocieteAsserts.*;
import static mc.sbm.simphonycloud.domain.SocieteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocieteMapperTest {

    private SocieteMapper societeMapper;

    @BeforeEach
    void setUp() {
        societeMapper = new SocieteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSocieteSample1();
        var actual = societeMapper.toEntity(societeMapper.toDto(expected));
        assertSocieteAllPropertiesEquals(expected, actual);
    }
}
