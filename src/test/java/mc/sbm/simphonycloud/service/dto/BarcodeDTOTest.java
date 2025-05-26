package mc.sbm.simphonycloud.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mc.sbm.simphonycloud.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BarcodeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeDTO.class);
        BarcodeDTO barcodeDTO1 = new BarcodeDTO();
        barcodeDTO1.setId(1L);
        BarcodeDTO barcodeDTO2 = new BarcodeDTO();
        assertThat(barcodeDTO1).isNotEqualTo(barcodeDTO2);
        barcodeDTO2.setId(barcodeDTO1.getId());
        assertThat(barcodeDTO1).isEqualTo(barcodeDTO2);
        barcodeDTO2.setId(2L);
        assertThat(barcodeDTO1).isNotEqualTo(barcodeDTO2);
        barcodeDTO1.setId(null);
        assertThat(barcodeDTO1).isNotEqualTo(barcodeDTO2);
    }
}
