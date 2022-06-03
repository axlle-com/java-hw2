package tracker.model;

import tracker.database.DataBase;
import java.util.HashMap;

public class EpicTask extends Task {
    public EpicTask(String title, String description, Status status) {
        super(title, description, status);
    }

    @Override
    public String toString() {
        return "EpicTask{"
                + "uuid='" + uuid + "'"
                + ", title='" + title + "'"
                + ", descriptionLength='" + description.length() + "'"
                + ", status=" + status + "}";
    }

    public HashMap<String, Task> getChildrenTask(){
        DataBase dataBase = DataBase.getInstance();
        HashMap<String, Task> childrenTask = new HashMap<>();
        for (Task task : dataBase.getTableTask().values()) {
            if (task instanceof SubTask) {
                if (this == ((SubTask) task).getParentTask()) {
                    childrenTask.put(task.getUuid(), task);
                }
            }
        }
        return childrenTask;
    }
}
