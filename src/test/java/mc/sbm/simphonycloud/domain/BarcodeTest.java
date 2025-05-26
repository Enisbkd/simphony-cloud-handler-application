package mc.sbm.simphonycloud.domain;

import static mc.sbm.simphonycloud.domain.BarcodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BarcodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Barcode.class);
        Barcode barcode1 = getBarcodeSample1();
        Barcode barcode2 = new Barcode();
        assertThat(barcode1).isNotEqualTo(barcode2);

        barcode2.setId(barcode1.getId());
        assertThat(barcode1).isEqualTo(barcode2);

        barcode2 = getBarcodeSample2();
        assertThat(barcode1).isNotEqualTo(barcode2);
    }
}
