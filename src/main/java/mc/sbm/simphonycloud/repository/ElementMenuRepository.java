package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.ElementMenu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ElementMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementMenuRepository extends JpaRepository<ElementMenu, Integer> {}
