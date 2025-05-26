package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PartieDeJourneeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PartieDeJournee getPartieDeJourneeSample1() {
        return new PartieDeJournee().id(1).timeRangeStart("timeRangeStart1").timeRangeEnd("timeRangeEnd1").nom("nom1");
    }

    public static PartieDeJournee getPartieDeJourneeSample2() {
        return new PartieDeJournee().id(2).timeRangeStart("timeRangeStart2").timeRangeEnd("timeRangeEnd2").nom("nom2");
    }

    public static PartieDeJournee getPartieDeJourneeRandomSampleGenerator() {
        return new PartieDeJournee()
            .id(intCount.incrementAndGet())
            .timeRangeStart(UUID.randomUUID().toString())
            .timeRangeEnd(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString());
    }
}
