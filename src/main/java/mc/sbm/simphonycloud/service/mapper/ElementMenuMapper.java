package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.ElementMenu;
import mc.sbm.simphonycloud.service.dto.ElementMenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ElementMenu} and its DTO {@link ElementMenuDTO}.
 */
@Mapper(componentModel = "spring")
public interface ElementMenuMapper extends EntityMapper<ElementMenuDTO, ElementMenu> {}
