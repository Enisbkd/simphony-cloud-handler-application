package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Hier;
import mc.sbm.simphonycloud.service.dto.HierDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hier} and its DTO {@link HierDTO}.
 */
@Mapper(componentModel = "spring")
public interface HierMapper extends EntityMapper<HierDTO, Hier> {}
