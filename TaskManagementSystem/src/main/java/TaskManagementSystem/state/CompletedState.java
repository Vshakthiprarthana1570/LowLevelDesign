package TaskManagementSystem.state;

import TaskManagementSystem.enums.TaskStatus;
import TaskManagementSystem.models.Task;

public class CompletedState implements TaskState
{

        public void startProgress(Task task)
        {
            System.out.println("Task is already in progress.");
        }

        public void completeTask(Task task)
        {
            System.out.println("Task is already done.");
        }

        public void reopenTask(Task task)
        {
            task.setState(new TodoState());
        }

        @Override
        public TaskStatus getStatus()
        {
            return TaskStatus.IN_PROGRESS;
        }
}
