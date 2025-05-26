package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.ModePaiement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ModePaiement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModePaiementRepository extends JpaRepository<ModePaiement, Integer> {}
