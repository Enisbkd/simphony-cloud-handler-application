package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ElementMenuTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ElementMenu getElementMenuSample1() {
        return new ElementMenu().id(1).masterId(1).nom("nom1").nomCourt("nomCourt1").familyGroupRef(1).prix(1).menuRef(1);
    }

    public static ElementMenu getElementMenuSample2() {
        return new ElementMenu().id(2).masterId(2).nom("nom2").nomCourt("nomCourt2").familyGroupRef(2).prix(2).menuRef(2);
    }

    public static ElementMenu getElementMenuRandomSampleGenerator() {
        return new ElementMenu()
            .id(intCount.incrementAndGet())
            .masterId(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .familyGroupRef(intCount.incrementAndGet())
            .prix(intCount.incrementAndGet())
            .menuRef(intCount.incrementAndGet());
    }
}
