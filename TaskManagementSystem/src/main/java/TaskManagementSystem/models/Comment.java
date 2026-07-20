package TaskManagementSystem.models;

public class Comment
{
    private final String id;
    private final String content;
    private final User author;

    public Comment(String content, User user)
    {
        this.id = java.util.UUID.randomUUID().toString();
        this.content = content;
        this.author = user;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }
}
