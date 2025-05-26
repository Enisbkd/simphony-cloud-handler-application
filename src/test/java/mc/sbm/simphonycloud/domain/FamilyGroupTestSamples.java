package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FamilyGroupTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FamilyGroup getFamilyGroupSample1() {
        return new FamilyGroup().id(1).nom("nom1").nomCourt("nomCourt1").majorGroupRef(1);
    }

    public static FamilyGroup getFamilyGroupSample2() {
        return new FamilyGroup().id(2).nom("nom2").nomCourt("nomCourt2").majorGroupRef(2);
    }

    public static FamilyGroup getFamilyGroupRandomSampleGenerator() {
        return new FamilyGroup()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .majorGroupRef(intCount.incrementAndGet());
    }
}
