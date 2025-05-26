package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Societe;
import mc.sbm.simphonycloud.service.dto.SocieteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Societe} and its DTO {@link SocieteDTO}.
 */
@Mapper(componentModel = "spring")
public interface SocieteMapper extends EntityMapper<SocieteDTO, Societe> {}
