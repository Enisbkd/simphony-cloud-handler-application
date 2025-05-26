package mc.sbm.simphonycloud.service.mapper;

import mc.sbm.simphonycloud.domain.Barcode;
import mc.sbm.simphonycloud.service.dto.BarcodeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Barcode} and its DTO {@link BarcodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BarcodeMapper extends EntityMapper<BarcodeDTO, Barcode> {}
