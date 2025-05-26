package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Employe;
import mc.sbm.simphonycloud.service.dto.EmployeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employe} and its DTO {@link EmployeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeMapper extends EntityMapper<EmployeDTO, Employe> {}
