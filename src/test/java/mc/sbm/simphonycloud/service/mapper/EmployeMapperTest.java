package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.EmployeAsserts.*;
import static mc.sbm.simphonycloud.domain.EmployeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeMapperTest {

    private EmployeMapper employeMapper;

    @BeforeEach
    void setUp() {
        employeMapper = new EmployeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEmployeSample1();
        var actual = employeMapper.toEntity(employeMapper.toDto(expected));
        assertEmployeAllPropertiesEquals(expected, actual);
    }
}
