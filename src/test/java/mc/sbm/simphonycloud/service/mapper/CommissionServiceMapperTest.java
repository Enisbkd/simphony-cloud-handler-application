package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.CommissionServiceAsserts.*;
import static mc.sbm.simphonycloud.domain.CommissionServiceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommissionServiceMapperTest {

    private CommissionServiceMapper commissionServiceMapper;

    @BeforeEach
    void setUp() {
        commissionServiceMapper = new CommissionServiceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCommissionServiceSample1();
        var actual = commissionServiceMapper.toEntity(commissionServiceMapper.toDto(expected));
        assertCommissionServiceAllPropertiesEquals(expected, actual);
    }
}
