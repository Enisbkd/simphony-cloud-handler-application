package mc.sbm.simphonycloud.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CategoriePointDeVenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CategoriePointDeVente getCategoriePointDeVenteSample1() {
        return new CategoriePointDeVente().id(1).categorie("categorie1").etablissementRef(1);
    }

    public static CategoriePointDeVente getCategoriePointDeVenteSample2() {
        return new CategoriePointDeVente().id(2).categorie("categorie2").etablissementRef(2);
    }

    public static CategoriePointDeVente getCategoriePointDeVenteRandomSampleGenerator() {
        return new CategoriePointDeVente()
            .id(intCount.incrementAndGet())
            .categorie(UUID.randomUUID().toString())
            .etablissementRef(intCount.incrementAndGet());
    }
}
