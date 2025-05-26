package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.FamilyGroup;
import mc.sbm.simphonycloud.service.dto.FamilyGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyGroup} and its DTO {@link FamilyGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface FamilyGroupMapper extends EntityMapper<FamilyGroupDTO, FamilyGroup> {}
