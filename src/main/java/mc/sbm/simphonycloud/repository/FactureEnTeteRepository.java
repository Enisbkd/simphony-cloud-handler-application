package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.FactureEnTete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FactureEnTete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureEnTeteRepository extends JpaRepository<FactureEnTete, Integer> {}
