package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Menu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Menu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {}
