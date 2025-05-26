package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CommissionService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CommissionService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionServiceRepository extends JpaRepository<CommissionService, Integer> {}
