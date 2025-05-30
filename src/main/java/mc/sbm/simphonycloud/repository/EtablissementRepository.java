package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.Etablissement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Etablissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, String> {}
