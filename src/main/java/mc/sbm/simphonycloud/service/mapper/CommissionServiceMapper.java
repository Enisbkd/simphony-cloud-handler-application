package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.CommissionService;
import mc.sbm.simphonycloud.service.dto.CommissionServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommissionService} and its DTO {@link CommissionServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommissionServiceMapper extends EntityMapper<CommissionServiceDTO, CommissionService> {}
