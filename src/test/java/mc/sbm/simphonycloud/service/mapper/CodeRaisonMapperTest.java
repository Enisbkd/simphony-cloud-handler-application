package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.CodeRaisonAsserts.*;
import static mc.sbm.simphonycloud.domain.CodeRaisonTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CodeRaisonMapperTest {

    private CodeRaisonMapper codeRaisonMapper;

    @BeforeEach
    void setUp() {
        codeRaisonMapper = new CodeRaisonMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCodeRaisonSample1();
        var actual = codeRaisonMapper.toEntity(codeRaisonMapper.toDto(expected));
        assertCodeRaisonAllPropertiesEquals(expected, actual);
    }
}
