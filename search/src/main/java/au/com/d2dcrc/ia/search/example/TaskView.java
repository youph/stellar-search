package au.com.d2dcrc.ia.search.example;

import java.time.ZonedDateTime;

/**
 * Encapsulates a view of a task returned to the user.
 */
public class TaskView {

    private final long id;
    private final String principle;
    private final String name;
    private final ZonedDateTime dateTime;
    private final String status;
    private final String description;

    /**
     * Constructor.
     *
     * @param id the id of the task
     * @param principle the principle username who created the task
     * @param name the name of the task
     * @param description description of the task
     * @param dateTime some datetime
     * @param status task status
     */
    TaskView(
        final long id,
        final String principle,
        final String name,
        final String description,
        final ZonedDateTime dateTime,
        final String status
    ) {
        this.id = id;
        this.principle = principle;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }

    /**
     * Gets the task ID.
     *
     * @return the task ID
     */
    public long getId() {
        return id;
    }

    /**
     * Get the task name.
     * @return the task name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the task description.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get some datetime.
     *
     * @return some datetime
     */
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Get status of task.
     *
     * @return task status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Get the principle username who created the task.
     *
     * @return the principle user
     */
    public String getPrinciple() {
        return principle;
    }

}
