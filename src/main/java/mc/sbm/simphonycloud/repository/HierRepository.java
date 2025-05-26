package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Hier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Hier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HierRepository extends JpaRepository<Hier, String> {}
