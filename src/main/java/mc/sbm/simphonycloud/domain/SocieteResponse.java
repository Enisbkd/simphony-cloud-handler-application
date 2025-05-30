package mc.sbm.simphonycloud.domain;

import java.util.List;
import lombok.Data;

@Data
public class SocieteResponse {

    private List<Societe> items;
    private Integer limit;
    private Integer count;
    private Integer offset;
}
