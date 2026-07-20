package TaskManagementSystem.strategy;

import TaskManagementSystem.models.Task;

import java.util.List;

public interface TaskSortStrategy
{
    void sort(List<Task> tasks);
}
