package au.com.d2dcrc.ia.search.example;

import au.com.d2dcrc.ia.search.user.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Service for managing 'tasks'.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EntityManager entityManager;

    /**
     * Constructor.
     *
     * @param taskRepository the spring jpa task repository
     * @param entityManager hibernate entity manager
     */
    @Inject
    public TaskService(
        final TaskRepository taskRepository,
        final EntityManager entityManager
    ) {
        this.taskRepository = taskRepository;
        this.entityManager = entityManager;
    }

    /**
     * Gets a list of all tasks.
     *
     * @return a list of all tasks
     */
    public List<TaskView> findAllTasks() {
        return taskRepository.findAll()
            .stream()
            .map(TaskEntity::toView)
            .collect(Collectors.toList());
    }

    /**
     * Creates a new task with the user provided model,
     * and the username of the principle issuing the request from, obtained
     * from the current context.
     *
     * @param task the task paramters provided by the user
     * @param principleName the user requesting a new task
     * @return a task view created with new ID
     */
    public TaskView createTask(TaskModel task, String principleName) {

        final User userDao = entityManager.getReference(User.class, principleName);

        final TaskEntity taskEntity = new TaskEntity(
            task.getName(),
            task.getDescription(),
            task.getDateTime(),
            "Accepted",
            userDao
        );

        return taskRepository.saveAndFlush(taskEntity)
            .toView();

    }

    /**
     * Finds a task with the specified ID or null if no task found.
     *
     * @param id the id of the task to find
     * @return a task view
     */
    public TaskView findTask(long id) {
        final TaskEntity entity = taskRepository.findOne(id);
        return (entity == null) ? null : entity.toView();
    }

    /**
     * Deletes a task with the specified ID from the repository.
     *
     * @param id if of task to delete
     */
    public void deleteTask(long id) {
        taskRepository.delete(id);
    }
}
