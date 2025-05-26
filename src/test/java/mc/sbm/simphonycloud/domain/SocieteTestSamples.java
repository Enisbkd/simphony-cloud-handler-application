package mc.sbm.simphonycloud.domain;

import java.util.UUID;

public class SocieteTestSamples {

    public static Societe getSocieteSample1() {
        return new Societe().id("id1").nom("nom1").nomCourt("nomCourt1");
    }

    public static Societe getSocieteSample2() {
        return new Societe().id("id2").nom("nom2").nomCourt("nomCourt2");
    }

    public static Societe getSocieteRandomSampleGenerator() {
        return new Societe().id(UUID.randomUUID().toString()).nom(UUID.randomUUID().toString()).nomCourt(UUID.randomUUID().toString());
    }
}
