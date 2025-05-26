package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.CategoriePointDeVente;
import mc.sbm.simphonycloud.service.dto.CategoriePointDeVenteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CategoriePointDeVente} and its DTO {@link CategoriePointDeVenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriePointDeVenteMapper extends EntityMapper<CategoriePointDeVenteDTO, CategoriePointDeVente> {}
