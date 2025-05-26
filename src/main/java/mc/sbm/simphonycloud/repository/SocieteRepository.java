package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Societe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Societe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocieteRepository extends JpaRepository<Societe, String> {}
