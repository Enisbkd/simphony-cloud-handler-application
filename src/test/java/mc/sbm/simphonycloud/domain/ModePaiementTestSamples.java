package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ModePaiementTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ModePaiement getModePaiementSample1() {
        return new ModePaiement()
            .id(1)
            .nom("nom1")
            .nomCourt("nomCourt1")
            .nomMstr("nomMstr1")
            .type("type1")
            .etablissementRef("etablissementRef1");
    }

    public static ModePaiement getModePaiementSample2() {
        return new ModePaiement()
            .id(2)
            .nom("nom2")
            .nomCourt("nomCourt2")
            .nomMstr("nomMstr2")
            .type("type2")
            .etablissementRef("etablissementRef2");
    }

    public static ModePaiement getModePaiementRandomSampleGenerator() {
        return new ModePaiement()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .nomMstr(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
