package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.PointDeVente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointDeVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointDeVenteRepository extends JpaRepository<PointDeVente, Integer> {}
