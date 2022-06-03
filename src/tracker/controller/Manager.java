package tracker.controller;

import tracker.database.DataBase;
import tracker.model.EpicTask;
import tracker.model.SubTask;
import tracker.model.Task;
import java.util.HashMap;

public class Manager {
    private DataBase dataBase;

    public Manager() {
        dataBase = DataBase.getInstance();
    }

    //Получение списка всех задач
    public HashMap<String, Task> getTasks() {
        return dataBase.getTableTask();
    }

    //Получение списка всех эпиков
    public HashMap<String, Task> getEpicTasks() {
        HashMap<String, Task> taskList = new HashMap<>();
        for (Task task : dataBase.getTableTask().values()) {
            if (task instanceof EpicTask) {
                taskList.put(task.getUuid(), task);
            }
        }
        return taskList;
    }

    //Получение списка всех подзадач определённого эпика
    public HashMap<String, Task> getTasksForEpicTask(String uuid) {
        Task epicTask = dataBase.getTableTask().get(uuid);
        if (epicTask instanceof EpicTask) {
            return ((EpicTask) epicTask).getChildrenTask();
        }
        return null;
    }

    //Получение задачи любого типа по идентификатору
    public Task getTaskByUuid(String uuid) {
        return dataBase.getTableTask().get(uuid);
    }

    //Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве параметра
    public void putTaskToDataBase(Task task) {
        if (!dataBase.getTableTask().containsValue(task)) {
            dataBase.getTableTask().put(task.getUuid(), task);
        }
    }

    //Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра
    public void updateTaskToDataBase(String uuid, Task task) {
        Task taskInBase = dataBase.getTableTask().get(uuid);
        if (taskInBase != null) {
            taskInBase.update(task);
        }
    }

    //Удаление ранее добавленных задач — всех
    public void deleteAllTasks() {
        dataBase.getTableTask().clear();
    }

    //Удаление ранее добавленных задач — по идентификатору
    public void deleteTaskByUuid(String uuid) {
        Task taskInBase = dataBase.getTableTask().get(uuid);
        if (taskInBase != null) {
            if (taskInBase instanceof EpicTask) {
                //удаляем все задачи принадлежащие текущему epic
                for (Task task : dataBase.getTableTask().values()) {
                    if (task instanceof SubTask) {
                        if (taskInBase == ((SubTask) task).getParentTask()) {
                            dataBase.getTableTask().remove(task.getUuid());
                        }
                    }
                }
            }
            dataBase.getTableTask().remove(uuid);
        }
    }
}
