package TaskManagementSystem;

import TaskManagementSystem.enums.TaskPriority;
import TaskManagementSystem.enums.TaskStatus;
import TaskManagementSystem.models.Task;
import TaskManagementSystem.models.TaskList;
import TaskManagementSystem.models.User;
import TaskManagementSystem.observer.ActivityObserver;
import TaskManagementSystem.strategy.TaskSortStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskManagementSystem
{
    private static TaskManagementSystem instance;
    private final Map<String, User> users;
    private final Map<String, Task> tasks;
    private final Map<String, TaskList> taskLists;

    private TaskManagementSystem()
    {
        users = new ConcurrentHashMap<>();
        tasks = new ConcurrentHashMap<>();
        taskLists = new ConcurrentHashMap<>();
    }

    public static synchronized TaskManagementSystem getInstance() {
        if (instance == null) {
            instance = new TaskManagementSystem();
        }
        return instance;
    }

    public User createUser(String name, String email) {
        User user = new User(name, email);
        users.put(user.getId(), user);
        return user;
    }

    public TaskList createTaskList(String listName) {
        TaskList taskList = new TaskList(listName);
        taskLists.put(taskList.getId(), taskList);
        return taskList;
    }

    public Task createTask(String title, String description, LocalDate dueDate,
                           TaskPriority priority, String createdByUserId)
    {
        User createdBy = users.get(createdByUserId);
        if (createdBy == null)
            throw new IllegalArgumentException("User not found.");

        Task task = new Task.TaskBuilder(title)
                .setDescription(description)
                .setDueDate(dueDate)
                .setPriority(priority)
                .setCreatedBy(users.get(createdByUserId))
                .build();

        task.addObserver(new ActivityObserver());

        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> listTasksByUser(String userId)
    {
        User user = users.get(userId);
        if (user == null) {
            return List.of(); // Clean, immutable empty list
        }
        return tasks.values().stream()
                .filter(task -> user.equals(task.getAssignee()))
                .toList();
    }

    public List<Task> listTasksByStatus(TaskStatus status)
    {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public void deleteTask(String taskId) {
        tasks.remove(taskId);
    }

    public List<Task> searchTasks(String keyword, TaskSortStrategy sortingStrategy) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getTitle().contains(keyword) || task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        sortingStrategy.sort(matchingTasks);
        return matchingTasks;
    }
}
