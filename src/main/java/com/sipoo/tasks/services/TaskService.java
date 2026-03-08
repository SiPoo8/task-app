package com.sipoo.tasks.services;

import com.sipoo.tasks.entities.Task;
import com.sipoo.tasks.entities.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListID,Task task);
    Optional<Task> getTask(UUID taskListid,UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId,Task task);
    void deleteTask(UUID taskListId,UUID taskId);

}
