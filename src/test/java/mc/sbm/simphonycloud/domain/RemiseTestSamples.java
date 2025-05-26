package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RemiseTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Remise getRemiseSample1() {
        return new Remise().id(1).nom("nom1").nomCourt("nomCourt1").nomMstr("nomMstr1").typeValue("typeValue1").pointDeVenteRef(1);
    }

    public static Remise getRemiseSample2() {
        return new Remise().id(2).nom("nom2").nomCourt("nomCourt2").nomMstr("nomMstr2").typeValue("typeValue2").pointDeVenteRef(2);
    }

    public static Remise getRemiseRandomSampleGenerator() {
        return new Remise()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .nomCourt(UUID.randomUUID().toString())
            .nomMstr(UUID.randomUUID().toString())
            .typeValue(UUID.randomUUID().toString())
            .pointDeVenteRef(intCount.incrementAndGet());
    }
}
