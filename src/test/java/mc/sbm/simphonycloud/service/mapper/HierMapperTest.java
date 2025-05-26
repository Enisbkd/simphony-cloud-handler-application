package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.HierAsserts.*;
import static mc.sbm.simphonycloud.domain.HierTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HierMapperTest {

    private HierMapper hierMapper;

    @BeforeEach
    void setUp() {
        hierMapper = new HierMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHierSample1();
        var actual = hierMapper.toEntity(hierMapper.toDto(expected));
        assertHierAllPropertiesEquals(expected, actual);
    }
}
