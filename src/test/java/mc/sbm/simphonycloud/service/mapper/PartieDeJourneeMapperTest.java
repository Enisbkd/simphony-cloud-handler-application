package mc.sbm.simphonycloud.service.mapper;

import static mc.sbm.simphonycloud.domain.PartieDeJourneeAsserts.*;
import static mc.sbm.simphonycloud.domain.PartieDeJourneeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartieDeJourneeMapperTest {

    private PartieDeJourneeMapper partieDeJourneeMapper;

    @BeforeEach
    void setUp() {
        partieDeJourneeMapper = new PartieDeJourneeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartieDeJourneeSample1();
        var actual = partieDeJourneeMapper.toEntity(partieDeJourneeMapper.toDto(expected));
        assertPartieDeJourneeAllPropertiesEquals(expected, actual);
    }
}
