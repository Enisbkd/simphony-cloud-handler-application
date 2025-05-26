package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MajorGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MajorGroup getMajorGroupSample1() {
        return new MajorGroup().id(1).nom("nom1").nomCourt("nomCourt1").pointDeVenteRef(1);
    }

    public static MajorGroup getMajorGroupSample2() {
        return new MajorGroup().id(2).nom("nom2").nomCourt("nomCourt2").pointDeVenteRef(2);
    }

    public static MajorGroup getMajorGroupRandomSampleGenerator() {
        return new MajorGroup()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .pointDeVenteRef(intCount.incrementAndGet());
    }
}
