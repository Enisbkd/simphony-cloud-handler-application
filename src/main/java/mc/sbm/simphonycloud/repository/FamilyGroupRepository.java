package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.FamilyGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FamilyGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Integer> {}
