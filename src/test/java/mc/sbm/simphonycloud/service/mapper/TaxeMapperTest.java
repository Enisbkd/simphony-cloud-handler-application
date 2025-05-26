package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.TaxeAsserts.*;
import static mc.sbm.simphonycloud.domain.TaxeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaxeMapperTest {

    private TaxeMapper taxeMapper;

    @BeforeEach
    void setUp() {
        taxeMapper = new TaxeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTaxeSample1();
        var actual = taxeMapper.toEntity(taxeMapper.toDto(expected));
        assertTaxeAllPropertiesEquals(expected, actual);
    }
}
