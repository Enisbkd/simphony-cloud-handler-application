package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TaxeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Taxe getTaxeSample1() {
        return new Taxe().id(1).nom("nom1").nomCourt("nomCourt1").vatTaxRate(1).classId(1).taxType(1).etablissementRef("etablissementRef1");
    }

    public static Taxe getTaxeSample2() {
        return new Taxe().id(2).nom("nom2").nomCourt("nomCourt2").vatTaxRate(2).classId(2).taxType(2).etablissementRef("etablissementRef2");
    }

    public static Taxe getTaxeRandomSampleGenerator() {
        return new Taxe()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .vatTaxRate(intCount.incrementAndGet())
            .classId(intCount.incrementAndGet())
            .taxType(intCount.incrementAndGet())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
