package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Menu;
import mc.sbm.simphonycloud.service.dto.MenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring")
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {}
