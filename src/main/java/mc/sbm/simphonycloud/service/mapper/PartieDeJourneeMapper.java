package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.PartieDeJournee;
import mc.sbm.simphonycloud.service.dto.PartieDeJourneeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartieDeJournee} and its DTO {@link PartieDeJourneeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartieDeJourneeMapper extends EntityMapper<PartieDeJourneeDTO, PartieDeJournee> {}
