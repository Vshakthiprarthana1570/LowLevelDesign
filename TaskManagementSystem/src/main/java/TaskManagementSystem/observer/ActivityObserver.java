package TaskManagementSystem.observer;

import TaskManagementSystem.models.Task;

public class ActivityObserver implements TaskObserver
{
    public void update(Task task, String message)
    {
        System.out.println("Activity Log: Task '" + task.getTitle() + "' has been updated. " + message);
    }
}
