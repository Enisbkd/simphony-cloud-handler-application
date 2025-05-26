package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Taxe;
import mc.sbm.simphonycloud.service.dto.TaxeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taxe} and its DTO {@link TaxeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaxeMapper extends EntityMapper<TaxeDTO, Taxe> {}
