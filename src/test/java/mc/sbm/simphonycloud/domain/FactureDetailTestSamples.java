package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FactureDetailTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static FactureDetail getFactureDetailSample1() {
        return new FactureDetail()
            .id(1)
            .factureEnTeteRef(1)
            .numLigne(1)
            .detailType("detailType1")
            .numSiege("numSiege1")
            .quantiteAffiche(1)
            .totalLigne(1)
            .codeRaisonRef(1)
            .multiplicateur(1)
            .referenceInfo("referenceInfo1")
            .referenceInfo2("referenceInfo21")
            .partieDeJourneeRef(1)
            .periodeDeServiceRef(1)
            .numChrono(1)
            .parentFactureDetailRef(1)
            .taxePourcentage(1)
            .taxeMontant(1)
            .modePaiementTotal(1)
            .prix(1)
            .transactionEmployeRef(1)
            .transfertEmployeRef(1)
            .managerEmployeRef(1)
            .repasEmployeRef(1)
            .remiseRef(1)
            .remiseElementMenuRef(1)
            .commissionServiceRef(1)
            .modePaiementRef(1)
            .elementMenuRef(1);
    }

    public static FactureDetail getFactureDetailSample2() {
        return new FactureDetail()
            .id(2)
            .factureEnTeteRef(2)
            .numLigne(2)
            .detailType("detailType2")
            .numSiege("numSiege2")
            .quantiteAffiche(2)
            .totalLigne(2)
            .codeRaisonRef(2)
            .multiplicateur(2)
            .referenceInfo("referenceInfo2")
            .referenceInfo2("referenceInfo22")
            .partieDeJourneeRef(2)
            .periodeDeServiceRef(2)
            .numChrono(2)
            .parentFactureDetailRef(2)
            .taxePourcentage(2)
            .taxeMontant(2)
            .modePaiementTotal(2)
            .prix(2)
            .transactionEmployeRef(2)
            .transfertEmployeRef(2)
            .managerEmployeRef(2)
            .repasEmployeRef(2)
            .remiseRef(2)
            .remiseElementMenuRef(2)
            .commissionServiceRef(2)
            .modePaiementRef(2)
            .elementMenuRef(2);
    }

    public static FactureDetail getFactureDetailRandomSampleGenerator() {
        return new FactureDetail()
            .id(intCount.incrementAndGet())
            .factureEnTeteRef(intCount.incrementAndGet())
            .numLigne(intCount.incrementAndGet())
            .detailType(UUID.randomUUID().toString())
            .numSiege(UUID.randomUUID().toString())
            .quantiteAffiche(intCount.incrementAndGet())
            .totalLigne(intCount.incrementAndGet())
            .codeRaisonRef(intCount.incrementAndGet())
            .multiplicateur(intCount.incrementAndGet())
            .referenceInfo(UUID.randomUUID().toString())
            .referenceInfo2(UUID.randomUUID().toString())
            .partieDeJourneeRef(intCount.incrementAndGet())
            .periodeDeServiceRef(intCount.incrementAndGet())
            .numChrono(intCount.incrementAndGet())
            .parentFactureDetailRef(intCount.incrementAndGet())
            .taxePourcentage(intCount.incrementAndGet())
            .taxeMontant(intCount.incrementAndGet())
            .modePaiementTotal(intCount.incrementAndGet())
            .prix(intCount.incrementAndGet())
            .transactionEmployeRef(intCount.incrementAndGet())
            .transfertEmployeRef(intCount.incrementAndGet())
            .managerEmployeRef(intCount.incrementAndGet())
            .repasEmployeRef(intCount.incrementAndGet())
            .remiseRef(intCount.incrementAndGet())
            .remiseElementMenuRef(intCount.incrementAndGet())
            .commissionServiceRef(intCount.incrementAndGet())
            .modePaiementRef(intCount.incrementAndGet())
            .elementMenuRef(intCount.incrementAndGet());
    }
}
