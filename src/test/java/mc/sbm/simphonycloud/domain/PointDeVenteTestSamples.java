package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PointDeVenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PointDeVente getPointDeVenteSample1() {
        return new PointDeVente().id(1).nom("nom1").nomCourt("nomCourt1").etablissementRef("etablissementRef1").hierRef("hierRef1");
    }

    public static PointDeVente getPointDeVenteSample2() {
        return new PointDeVente().id(2).nom("nom2").nomCourt("nomCourt2").etablissementRef("etablissementRef2").hierRef("hierRef2");
    }

    public static PointDeVente getPointDeVenteRandomSampleGenerator() {
        return new PointDeVente()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString())
            .hierRef(UUID.randomUUID().toString());
    }
}
