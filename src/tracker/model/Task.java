package tracker.model;

import java.util.UUID;

public class Task {
    protected String uuid;
    protected String title;
    protected String description;
    protected Status status;

    public Task(String title, String description, Status status) {
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return title.equals(task.title) && description.equals(task.description);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (title != null) {
            hash += title.hashCode();
        }
        hash *= 31;
        if (description != null) {
            hash += description.hashCode();
        }
        return hash;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void update(Task task) {
        this.setTitle(task.title);
        this.setDescription(task.description);
        this.setStatus(task.status);
    }
}
