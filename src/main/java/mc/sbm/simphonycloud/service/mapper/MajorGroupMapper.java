package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.MajorGroup;
import mc.sbm.simphonycloud.service.dto.MajorGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MajorGroup} and its DTO {@link MajorGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface MajorGroupMapper extends EntityMapper<MajorGroupDTO, MajorGroup> {}
