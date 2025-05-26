package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Employe getEmployeSample1() {
        return new Employe()
            .id(1)
            .num(1)
            .firstName("firstName1")
            .lastName("lastName1")
            .userName("userName1")
            .etablissementRef("etablissementRef1");
    }

    public static Employe getEmployeSample2() {
        return new Employe()
            .id(2)
            .num(2)
            .firstName("firstName2")
            .lastName("lastName2")
            .userName("userName2")
            .etablissementRef("etablissementRef2");
    }

    public static Employe getEmployeRandomSampleGenerator() {
        return new Employe()
            .id(intCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .userName(UUID.randomUUID().toString())
            .etablissementRef(UUID.randomUUID().toString());
    }
}
