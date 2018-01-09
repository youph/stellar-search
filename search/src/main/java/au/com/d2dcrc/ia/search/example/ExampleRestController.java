package au.com.d2dcrc.ia.search.example;

import au.com.d2dcrc.ia.search.security.AuthenticationFilter;
import au.com.d2dcrc.ia.search.status.ErrorView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * An example rest controller for creating / getting and deleting 'tasks'. Doesn't actually do anything!
 * The tasks are meaningless!
 */
@Api(description = "Example controller")
@RestController
@ApiResponses({
    @ApiResponse(code = 403, message = "Accessing the requested resource is forbidden", response = ErrorView.class),
    @ApiResponse(code = 404, message = "The requested resource was not found", response = ErrorView.class)
})
@RequestMapping(
    value = "/api/v1/example",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class ExampleRestController {

    /**
     * Simple granted authority for a principle who can create tasks.
     */
    public static final GrantedAuthority CAN_CREATE_TASKS_AUTHORITY = new SimpleGrantedAuthority(
        "EXAMPLE::CAN_CREATE_TASKS"
    );

    private static final Logger logger = LoggerFactory.getLogger(ExampleRestController.class);

    private final TaskService taskService;

    /**
     * Constructor.
     *
     * @param taskService the task service to injected into the controller
     */
    public ExampleRestController(final TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get a list of tasks.
     *
     * @return a list of task views
     */
    @ApiOperation("Retrieve all tasks")
    @ApiImplicitParams(
        @ApiImplicitParam(
            name = HttpHeaders.AUTHORIZATION,
            paramType = "header",
            required = true
        )
    )
    @RequestMapping(value = "/task/", method = RequestMethod.GET)
    public ResponseEntity<List<TaskView>> listAllTasks() {
        final List<TaskView> users = taskService.findAllTasks();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Creates a task. The new task is associated with the current authenticated principal username.
     * Authentication context is set by the {@link AuthenticationFilter}.
     *
     * @param task a model of the task to create
     * @param principal the authenticated user principle
     * @return a HTTP 201 Created response entity on success.
     */
    @ApiOperation(value = "Create a task", code = org.apache.http.HttpStatus.SC_CREATED)
    @ApiImplicitParams(
        @ApiImplicitParam(
            name = HttpHeaders.AUTHORIZATION,
            paramType = "header",
            required = true
        )
    )
    @RequestMapping(value = "/task/", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('EXAMPLE::CAN_CREATE_TASKS')")
    public ResponseEntity<?> createTask(
        @ApiParam(value = "task to create", required = true)
        @Valid
        @RequestBody
        final TaskModel task,
        final Principal principal
    ) {

        logger.debug("Creating Task : {}", task);

        final TaskView newTask = taskService.createTask(task, principal.getName());

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .pathSegment("{id}")
            .buildAndExpand(newTask.getId())
            .toUri();

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * Deletes a task.
     *
     * @param id the ID of the task to delete
     * @return HTTP 204 No Content on success
     */
    @ApiOperation(value = "Delete a task", code = org.apache.http.HttpStatus.SC_NO_CONTENT)
    @ApiImplicitParams(
        @ApiImplicitParam(
            name = HttpHeaders.AUTHORIZATION,
            paramType = "header",
            required = true
        )
    )
    @RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTask(
        @ApiParam(value = "task id", required = true)
        @PathVariable("id") long id
    ) {
        logger.debug("Deleting Task with id {}", id);

        final TaskView currentTask = taskService.findTask(id);

        if (currentTask == null) {
            logger.error("Unable to delete. Task with id {} not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taskService.deleteTask(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
