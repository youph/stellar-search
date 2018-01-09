package au.com.d2dcrc.ia.search.example;

import au.com.d2dcrc.ia.search.user.User;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Task entity for persistence via JPA.
 *
 * <p>Just a simple data structure representing some fields including a datetime and timezone
 * (because that is very annoying to represent in SQL - TIMESTAMP WITH TIMEZONE type means is that dates are
 * normalised and stored in UTC (which is still better local TIMESTAMP (WITHOUT TIMEZONE)) but it means the offset
 * is lost. Demonstrates the use of {@link au.com.d2dcrc.ia.search.common.ZoneIdConverter} which is discovered
 * automatically by hibernate classpath scanning.
 *
 * <p>Also demonstrations a entity relation between tasks and the
 * principle who created the task.
 */
@Entity
@Table(name = "example_task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "example_task_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = true)
    private String description;
    @Column(nullable = false)
    private Instant dateTimeInstant;
    @Column(nullable = false)
    private ZoneId dateTimeZoneId;
    @Column(nullable = false)
    private String status;
    @OneToOne
    @JoinColumn(name = "principle_name")
    private User principle;

    /**
     * No-arg constructor needed for Hibernate for reading a task from persistence.
     */
    private TaskEntity() {
    }

    /**
     * Constructs a new task for persisting in a repository.
     *
     * @param name name of task
     * @param description optional description of task
     * @param dateTime the zoned date time to persist
     * @param status the status of the task
     * @param principle the user who created the task
     */
    public TaskEntity(
        final String name,
        final String description,
        final ZonedDateTime dateTime,
        final String status,
        final User principle
    ) {
        this.name = name;
        this.description = description;
        this.dateTimeInstant = dateTime.toInstant();
        this.dateTimeZoneId = dateTime.getZone();
        this.status = status;
        this.principle = principle;
    }

    /**
     * Converts this JPA entity to a Task view.
     *
     * @return the view
     */
    public TaskView toView() {
        return new TaskView(
            this.id,
            this.principle.getUsername(),
            this.name,
            this.description,
            this.dateTimeInstant.atZone(this.dateTimeZoneId),
            this.status
        );
    }

}
