package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Remise;
import mc.sbm.simphonycloud.service.dto.RemiseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Remise} and its DTO {@link RemiseDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemiseMapper extends EntityMapper<RemiseDTO, Remise> {}
