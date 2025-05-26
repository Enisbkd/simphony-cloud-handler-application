package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.FactureDetail;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FactureDetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactureDetailRepository extends JpaRepository<FactureDetail, Integer> {}
