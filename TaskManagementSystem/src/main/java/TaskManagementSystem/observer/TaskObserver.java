package TaskManagementSystem.observer;

import TaskManagementSystem.models.Task;

public interface TaskObserver
{
    void update(Task task, String message);
}
