package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.ModePaiementAsserts.*;
import static mc.sbm.simphonycloud.domain.ModePaiementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModePaiementMapperTest {

    private ModePaiementMapper modePaiementMapper;

    @BeforeEach
    void setUp() {
        modePaiementMapper = new ModePaiementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getModePaiementSample1();
        var actual = modePaiementMapper.toEntity(modePaiementMapper.toDto(expected));
        assertModePaiementAllPropertiesEquals(expected, actual);
    }
}
