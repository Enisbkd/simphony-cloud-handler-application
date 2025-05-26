package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.CodeRaison;
import mc.sbm.simphonycloud.service.dto.CodeRaisonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CodeRaison} and its DTO {@link CodeRaisonDTO}.
 */
@Mapper(componentModel = "spring")
public interface CodeRaisonMapper extends EntityMapper<CodeRaisonDTO, CodeRaison> {}
