package au.com.d2dcrc.ia.search.example;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.ZonedDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Model of task to create posted by a user to the REST API.
 */
@ApiModel
@Validated
public class TaskModel {

    @ApiModelProperty(value = "task name", required = true)
    @NotEmpty
    private final String name;
    @ApiModelProperty(value = "task description")
    private final String description;
    @ApiModelProperty(value = "some IS8601 date time", required = true, example = "2015-05-09T16:15:00+10:30")
    @NotNull
    private final ZonedDateTime dateTime;

    /**
     * Constructs a new Task Model of a task to create.
     *
     * @param name name of the task
     * @param description an optional description
     * @param dateTime some datetime parsable by {@link ZonedDateTime#parse(CharSequence)}
     */
    public TaskModel(
        final String name,
        final String description,
        final ZonedDateTime dateTime
    ) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Gets the task name.
     * @return task name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the task description.
     * @return task description, may be null
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets some date time.
     * @return a date time
     */
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "TaskModel{"
            + "name='" + name + '\''
            + ", description=" + description
            + ", dateTimeSeconds=" + dateTime
            + '}';
    }
}
