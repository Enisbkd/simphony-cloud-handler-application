package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.FactureEnTete;
import mc.sbm.simphonycloud.service.dto.FactureEnTeteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FactureEnTete} and its DTO {@link FactureEnTeteDTO}.
 */
@Mapper(componentModel = "spring")
public interface FactureEnTeteMapper extends EntityMapper<FactureEnTeteDTO, FactureEnTete> {}
