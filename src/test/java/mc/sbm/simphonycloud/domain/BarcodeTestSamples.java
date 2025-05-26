package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class BarcodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Barcode getBarcodeSample1() {
        return new Barcode()
            .id(1)
            .num(1)
            .barcode("barcode1")
            .prix(1)
            .coutPreparation(1)
            .defNumSequence(1)
            .prixNumSequence(1)
            .pointDeVenteRef(1)
            .elementMenuRef(1);
    }

    public static Barcode getBarcodeSample2() {
        return new Barcode()
            .id(2)
            .num(2)
            .barcode("barcode2")
            .prix(2)
            .coutPreparation(2)
            .defNumSequence(2)
            .prixNumSequence(2)
            .pointDeVenteRef(2)
            .elementMenuRef(2);
    }

    public static Barcode getBarcodeRandomSampleGenerator() {
        return new Barcode()
            .id(intCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .barcode(UUID.randomUUID().toString())
            .prix(intCount.incrementAndGet())
            .coutPreparation(intCount.incrementAndGet())
            .defNumSequence(intCount.incrementAndGet())
            .prixNumSequence(intCount.incrementAndGet())
            .pointDeVenteRef(intCount.incrementAndGet())
            .elementMenuRef(intCount.incrementAndGet());
    }
}
