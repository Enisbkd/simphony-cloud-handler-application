package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.RemiseAsserts.*;
import static mc.sbm.simphonycloud.domain.RemiseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemiseMapperTest {

    private RemiseMapper remiseMapper;

    @BeforeEach
    void setUp() {
        remiseMapper = new RemiseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRemiseSample1();
        var actual = remiseMapper.toEntity(remiseMapper.toDto(expected));
        assertRemiseAllPropertiesEquals(expected, actual);
    }
}
