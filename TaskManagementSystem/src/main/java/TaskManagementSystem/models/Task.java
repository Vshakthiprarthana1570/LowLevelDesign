package TaskManagementSystem.models;

import TaskManagementSystem.enums.TaskPriority;
import TaskManagementSystem.enums.TaskStatus;
import TaskManagementSystem.observer.TaskObserver;
import TaskManagementSystem.state.TaskState;
import TaskManagementSystem.state.TodoState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Task
{
    private final String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private final User createdBy;
    private User assignee;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate createdAt;
    private TaskState currentState;
    private Set<Tag> tags;
    private List<Comment> comments;
    private List<ActivityLog> activityLogs;
    private List<Task> subTasks;
    private List<TaskObserver> observers;


    private Task(TaskBuilder taskBuilder)
    {
        this.id = taskBuilder.id;
        this.title = taskBuilder.title;
        this.description = taskBuilder.description;
        this.dueDate = taskBuilder.dueDate;
        this.assignee = taskBuilder.assignee;
        this.createdAt = LocalDate.now();
        this.createdBy = taskBuilder.createdBy;
        this.priority = taskBuilder.priority;
        this.status = TaskStatus.TODO;
        this.currentState = new TodoState();
        this.tags = taskBuilder.tags;
        this.subTasks = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.activityLogs = new ArrayList<>();
        addLog("Task Created");
    }


    public void addComment(Comment comment)
    {
        comments.add(comment);
        addLog("Comment added by " + comment.getAuthor().getName());
        notifyObservers("comment");
    }

    public synchronized void setState(TaskState state)
    {
        this.currentState = state;
        addLog("Status changed to " + state.getStatus());
        notifyObservers("State changed");
    }
    public void addLog(String description)
    {
        activityLogs.add(new ActivityLog(description));
    }

    public void startProgress() { currentState.startProgress(this); }
    public void completeTask() { currentState.completeTask(this); }
    public void reopenTask() { currentState.reopenTask(this); }

    public boolean isComposite() { return !subTasks.isEmpty(); }

    public void display(String indent) {
        System.out.println(indent + "- " + title + " [" + getStatus() + ", " + priority + ", Due: " + dueDate + "]");
        if (isComposite()) {
            for (Task subtask : subTasks) {
                subtask.display(indent + "  ");
            }
        }
    }

    public synchronized void addSubTask(Task subTask)
    {
        subTasks.add(subTask);
        addLog("Subtask Added: " + subTask.getTitle());
        notifyObservers("Subtask added");
    }

    public void addObserver(TaskObserver observer)
    {
        observers.add(observer);
    }

    public void removeObserver(TaskObserver observer)
    {
        observers.remove(observer);
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee)
    {
        this.assignee = assignee;
        addLog("Assignee Changed to " + assignee.getName());

    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public TaskState getTaskState() {
        return currentState;
    }


    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }


    public List<Task> getSubTasks() {
        return subTasks;
    }



    public List<TaskObserver> getObservers() {
        return observers;
    }



    public void notifyObservers(String changeType)
    {
        for(TaskObserver observer: observers)
        {
            observer.update(this,changeType);
        }
    }

    public static class TaskBuilder
    {
        private final String id;
        private String title;
        private String description = "";
        private LocalDate dueDate;
        private TaskPriority priority;
        private User createdBy;
        private User assignee;
        private Set<Tag> tags;


        public TaskBuilder(String title)
        {
            this.id = UUID.randomUUID().toString();
            this.title = title;
        }
        public TaskBuilder setDescription(String description)
        {
            this.description = description;
            return this;
        }
        public TaskBuilder setDueDate(LocalDate dueDate)
        {
            this.dueDate = dueDate;
            return this;
        }
        public TaskBuilder setPriority(TaskPriority priority)
        {
            this.priority = priority;
            return this;
        }
        public TaskBuilder setCreatedBy(User createdBy)
        {
            this.createdBy = createdBy;
            return this;
        }
        public TaskBuilder setAssignee(User assignee) {
            this.assignee = assignee;
            return this;
        }
        public TaskBuilder setTags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
