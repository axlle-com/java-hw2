package tracker.database;

import tracker.model.Task;
import java.util.HashMap;

public class DataBase {
    private static DataBase instance;
    private HashMap<String, Task> tableTask;

    private DataBase() {
        tableTask = new HashMap<>();
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public HashMap<String, Task> getTableTask() {
        return tableTask;
    }

    public void setTableTask(HashMap<String, Task> tableTask) {
        this.tableTask = tableTask;
    }
}
