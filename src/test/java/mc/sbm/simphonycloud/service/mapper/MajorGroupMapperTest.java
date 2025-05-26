package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.MajorGroupAsserts.*;
import static mc.sbm.simphonycloud.domain.MajorGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MajorGroupMapperTest {

    private MajorGroupMapper majorGroupMapper;

    @BeforeEach
    void setUp() {
        majorGroupMapper = new MajorGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMajorGroupSample1();
        var actual = majorGroupMapper.toEntity(majorGroupMapper.toDto(expected));
        assertMajorGroupAllPropertiesEquals(expected, actual);
    }
}
