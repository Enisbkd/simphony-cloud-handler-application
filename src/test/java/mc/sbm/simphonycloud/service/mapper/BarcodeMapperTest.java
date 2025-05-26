package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.BarcodeAsserts.*;
import static mc.sbm.simphonycloud.domain.BarcodeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BarcodeMapperTest {

    private BarcodeMapper barcodeMapper;

    @BeforeEach
    void setUp() {
        barcodeMapper = new BarcodeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBarcodeSample1();
        var actual = barcodeMapper.toEntity(barcodeMapper.toDto(expected));
        assertBarcodeAllPropertiesEquals(expected, actual);
    }
}
