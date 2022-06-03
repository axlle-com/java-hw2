package tracker.model;

import java.util.HashMap;

public class SubTask extends Task {
    private EpicTask parentTask;

    public SubTask(String title, String description, Status status, EpicTask parentTask) {
        super(title, description, status);
        this.parentTask = parentTask;
    }

    @Override
    public String toString() {
        return "SubTask{"
                + "uuid='" + uuid + "'"
                + ", parentTask='" + parentTask + "'"
                + ", title='" + title + "'"
                + ", descriptionLength='" + description.length() + "'"
                + ", status=" + status + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubTask task = (SubTask) o;
        return title.equals(task.title)
                && description.equals(task.description)
                && parentTask == task.parentTask;
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
        hash *= 31;
        if (parentTask != null) {
            hash += parentTask.hashCode();
        }
        return hash;
    }

    public EpicTask getParentTask() {
        return parentTask;
    }

    public void setParentTask(EpicTask parentTask) {
        this.parentTask = parentTask;
    }

    public void setStatus(Status status) {
        if (this.status.equals(status)) {
            return;
        }
        switch (status) {
            case NEW:
            case DONE:
                this.status = status;
                this.checkAndInstallEpicStatus(status);
                break;
            case IN_PROGRESS:
                this.status = status;
                this.getParentTask().setStatus(status);
                break;
        }
    }

    // Проверка у Эпика все ли таски одного статуса, если да, то меняем статус Эпика
    private void checkAndInstallEpicStatus(Status status) {
        HashMap<String, Task> taskList = this.getParentTask().getChildrenTask();
        HashMap<String, Task> taskListStatus = new HashMap<>();
        for (Task task : taskList.values()) {
            if (this.status.equals(task.getStatus())) {
                taskListStatus.put(task.getUuid(), task);
            }
        }
        if (taskList.size() == taskListStatus.size()) {
            this.getParentTask().setStatus(status);
        }
    }
}
