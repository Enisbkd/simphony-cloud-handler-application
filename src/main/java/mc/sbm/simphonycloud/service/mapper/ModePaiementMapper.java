package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.ModePaiement;
import mc.sbm.simphonycloud.service.dto.ModePaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ModePaiement} and its DTO {@link ModePaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModePaiementMapper extends EntityMapper<ModePaiementDTO, ModePaiement> {}
