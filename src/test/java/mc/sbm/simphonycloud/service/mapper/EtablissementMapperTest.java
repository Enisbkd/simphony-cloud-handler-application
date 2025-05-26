package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.EtablissementAsserts.*;
import static mc.sbm.simphonycloud.domain.EtablissementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EtablissementMapperTest {

    private EtablissementMapper etablissementMapper;

    @BeforeEach
    void setUp() {
        etablissementMapper = new EtablissementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEtablissementSample1();
        var actual = etablissementMapper.toEntity(etablissementMapper.toDto(expected));
        assertEtablissementAllPropertiesEquals(expected, actual);
    }
}
