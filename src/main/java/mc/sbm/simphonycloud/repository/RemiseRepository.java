package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Remise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Remise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemiseRepository extends JpaRepository<Remise, Integer> {}
