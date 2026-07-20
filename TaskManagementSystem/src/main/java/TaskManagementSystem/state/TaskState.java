package TaskManagementSystem.state;

import TaskManagementSystem.enums.TaskStatus;
import TaskManagementSystem.models.Task;

public interface TaskState
{
    void startProgress(Task task);
    void completeTask(Task task);
    void reopenTask(Task task);
    TaskStatus getStatus();
}
