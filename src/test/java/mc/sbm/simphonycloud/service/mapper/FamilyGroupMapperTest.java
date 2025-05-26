package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.FamilyGroupAsserts.*;
import static mc.sbm.simphonycloud.domain.FamilyGroupTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FamilyGroupMapperTest {

    private FamilyGroupMapper familyGroupMapper;

    @BeforeEach
    void setUp() {
        familyGroupMapper = new FamilyGroupMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFamilyGroupSample1();
        var actual = familyGroupMapper.toEntity(familyGroupMapper.toDto(expected));
        assertFamilyGroupAllPropertiesEquals(expected, actual);
    }
}
