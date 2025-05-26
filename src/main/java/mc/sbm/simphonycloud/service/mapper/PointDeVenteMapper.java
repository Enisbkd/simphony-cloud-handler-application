package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.PointDeVente;
import mc.sbm.simphonycloud.service.dto.PointDeVenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PointDeVente} and its DTO {@link PointDeVenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PointDeVenteMapper extends EntityMapper<PointDeVenteDTO, PointDeVente> {}
