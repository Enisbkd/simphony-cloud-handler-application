package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Taxe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Taxe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxeRepository extends JpaRepository<Taxe, Integer> {}
