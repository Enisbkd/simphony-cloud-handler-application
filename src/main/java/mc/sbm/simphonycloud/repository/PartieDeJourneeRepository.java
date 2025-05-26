package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.PartieDeJournee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PartieDeJournee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartieDeJourneeRepository extends JpaRepository<PartieDeJournee, Integer> {}
