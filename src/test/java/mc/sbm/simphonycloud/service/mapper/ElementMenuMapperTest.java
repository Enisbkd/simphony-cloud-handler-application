package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.ElementMenuAsserts.*;
import static mc.sbm.simphonycloud.domain.ElementMenuTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ElementMenuMapperTest {

    private ElementMenuMapper elementMenuMapper;

    @BeforeEach
    void setUp() {
        elementMenuMapper = new ElementMenuMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getElementMenuSample1();
        var actual = elementMenuMapper.toEntity(elementMenuMapper.toDto(expected));
        assertElementMenuAllPropertiesEquals(expected, actual);
    }
}
