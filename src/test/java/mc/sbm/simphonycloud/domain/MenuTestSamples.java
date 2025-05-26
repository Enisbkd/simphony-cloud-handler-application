package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Menu getMenuSample1() {
        return new Menu().id(1).nom("nom1").description("description1").etablissementRef("etablissementRef1");
    }

    public static Menu getMenuSample2() {
        return new Menu().id(2).nom("nom2").description("description2").etablissementRef("etablissementRef2");
    }

    public static Menu getMenuRandomSampleGenerator() {
        return new Menu()
            .id(intCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
