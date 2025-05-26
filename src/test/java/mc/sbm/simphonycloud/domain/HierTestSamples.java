package mc.sbm.simphonycloud.domain;

import java.util.UUID;

public class HierTestSamples {

    public static Hier getHierSample1() {
        return new Hier().id("id1").nom("nom1").parentHierId("parentHierId1").unitId("unitId1");
    }

    public static Hier getHierSample2() {
        return new Hier().id("id2").nom("nom2").parentHierId("parentHierId2").unitId("unitId2");
    }

    public static Hier getHierRandomSampleGenerator() {
        return new Hier()
            .id(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .parentHierId(UUID.randomUUID().toString())
            .unitId(UUID.randomUUID().toString());
    }
}
