package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Barcode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Barcode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Integer> {}
