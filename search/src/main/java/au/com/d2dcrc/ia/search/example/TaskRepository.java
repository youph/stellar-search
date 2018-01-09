package au.com.d2dcrc.ia.search.example;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Task JPA repository. Springs magic will provide the implementation that is injected into
 * dependent services.
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
