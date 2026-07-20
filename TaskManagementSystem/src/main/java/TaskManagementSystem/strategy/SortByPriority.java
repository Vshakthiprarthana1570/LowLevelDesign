package TaskManagementSystem.strategy;

import TaskManagementSystem.models.Task;

import java.util.Comparator;
import java.util.List;

public class SortByPriority implements TaskSortStrategy
{
    public void sort(List<Task> tasks)
    {
        tasks.sort(Comparator.comparing(Task::getPriority).reversed());
    }
}
