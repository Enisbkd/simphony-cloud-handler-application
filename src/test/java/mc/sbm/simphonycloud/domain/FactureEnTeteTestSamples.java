package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FactureEnTeteTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FactureEnTete getFactureEnTeteSample1() {
        return new FactureEnTete()
            .id(1)
            .num(1)
            .factureRef("factureRef1")
            .nbrePax(1)
            .numTable(1)
            .taxeMontantTotal(1)
            .sousTotal(1)
            .factureTotal(1)
            .commissionTotal(1)
            .tipTotal(1)
            .remiseTotal(1)
            .erreursCorrigeesTotal(1)
            .retourTotal(1)
            .xferToFactureEnTeteRef(1)
            .xferStatus("xferStatus1")
            .categoriePointDeVenteRef(1)
            .pointDeVenteRef(1);
    }

    public static FactureEnTete getFactureEnTeteSample2() {
        return new FactureEnTete()
            .id(2)
            .num(2)
            .factureRef("factureRef2")
            .nbrePax(2)
            .numTable(2)
            .taxeMontantTotal(2)
            .sousTotal(2)
            .factureTotal(2)
            .commissionTotal(2)
            .tipTotal(2)
            .remiseTotal(2)
            .erreursCorrigeesTotal(2)
            .retourTotal(2)
            .xferToFactureEnTeteRef(2)
            .xferStatus("xferStatus2")
            .categoriePointDeVenteRef(2)
            .pointDeVenteRef(2);
    }

    public static FactureEnTete getFactureEnTeteRandomSampleGenerator() {
        return new FactureEnTete()
            .id(intCount.incrementAndGet())
            .num(intCount.incrementAndGet())
            .factureRef(UUID.randomUUID().toString())
            .nbrePax(intCount.incrementAndGet())
            .numTable(intCount.incrementAndGet())
            .taxeMontantTotal(intCount.incrementAndGet())
            .sousTotal(intCount.incrementAndGet())
            .factureTotal(intCount.incrementAndGet())
            .commissionTotal(intCount.incrementAndGet())
            .tipTotal(intCount.incrementAndGet())
            .remiseTotal(intCount.incrementAndGet())
            .erreursCorrigeesTotal(intCount.incrementAndGet())
            .retourTotal(intCount.incrementAndGet())
            .xferToFactureEnTeteRef(intCount.incrementAndGet())
            .xferStatus(UUID.randomUUID().toString())
            .categoriePointDeVenteRef(intCount.incrementAndGet())
            .pointDeVenteRef(intCount.incrementAndGet());
    }
}
