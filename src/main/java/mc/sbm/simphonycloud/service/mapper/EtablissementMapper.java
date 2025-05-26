package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Etablissement;
import mc.sbm.simphonycloud.service.dto.EtablissementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Etablissement} and its DTO {@link EtablissementDTO}.
 */
@Mapper(componentModel = "spring")
public interface EtablissementMapper extends EntityMapper<EtablissementDTO, Etablissement> {}
