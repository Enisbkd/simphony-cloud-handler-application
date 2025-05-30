package mc.sbm.simphonycloud.service.dto;

import java.util.List;
import lombok.Data;
import mc.sbm.simphonycloud.domain.Societe;

@Data
public class SocietesResponse {

    private List<Societe> organizations;
    private int total;
}
