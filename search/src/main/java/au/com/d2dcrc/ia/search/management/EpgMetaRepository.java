package au.com.d2dcrc.ia.search.management;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for the EPG metadata.
 */
public interface EpgMetaRepository extends JpaRepository<EpgMetaEntity, String> {
}
