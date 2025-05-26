package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.MajorGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MajorGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MajorGroupRepository extends JpaRepository<MajorGroup, Integer> {}
