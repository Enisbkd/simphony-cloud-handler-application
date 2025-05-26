package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.FactureDetail;
import mc.sbm.simphonycloud.service.dto.FactureDetailDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FactureDetail} and its DTO {@link FactureDetailDTO}.
 */
@Mapper(componentModel = "spring")
public interface FactureDetailMapper extends EntityMapper<FactureDetailDTO, FactureDetail> {}
