package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CategoriePointDeVente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoriePointDeVente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriePointDeVenteRepository extends JpaRepository<CategoriePointDeVente, Integer> {}
