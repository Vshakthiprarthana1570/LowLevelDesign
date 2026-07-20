package TaskManagementSystem.models;

import java.util.UUID;

public class ActivityLog
{
    private final String id;
    private final String activityMessage;

    public ActivityLog(String activityMessage)
    {
        this.id = UUID.randomUUID().toString();
        this.activityMessage = activityMessage;
    }

    public String getId() {
        return id;
    }

    public String getActivityMessage() {
        return activityMessage;
    }
}
