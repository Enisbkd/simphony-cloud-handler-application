package mc.sbm.simphonycloud.repository;

import mc.sbm.simphonycloud.domain.CodeRaison;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CodeRaison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CodeRaisonRepository extends JpaRepository<CodeRaison, Integer> {}
