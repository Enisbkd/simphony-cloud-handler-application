package mc.sbm.simphonycloud.domain;

import java.util.UUID;

public class EtablissementTestSamples {

    public static Etablissement getEtablissementSample1() {
        return new Etablissement().id("id1").nom("nom1").sourceVersion("sourceVersion1").societeRef("societeRef1").hierRef("hierRef1");
    }

    public static Etablissement getEtablissementSample2() {
        return new Etablissement().id("id2").nom("nom2").sourceVersion("sourceVersion2").societeRef("societeRef2").hierRef("hierRef2");
    }

    public static Etablissement getEtablissementRandomSampleGenerator() {
        return new Etablissement()
            .id(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .sourceVersion(UUID.randomUUID().toString())
            .societeRef(UUID.randomUUID().toString())
            .hierRef(UUID.randomUUID().toString());
    }
}
