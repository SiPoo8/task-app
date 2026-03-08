package com.sipoo.tasks.services.impl;

import com.sipoo.tasks.entities.Task;
import com.sipoo.tasks.entities.TaskList;
import com.sipoo.tasks.entities.TaskPriority;
import com.sipoo.tasks.entities.TaskStatus;
import com.sipoo.tasks.repositories.TaskListRepository;
import com.sipoo.tasks.repositories.TaskRepository;
import com.sipoo.tasks.services.TaskService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;



    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(null!=task.getId()){
            throw new IllegalArgumentException("Task has and ID!");
        }
        if(null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(()->new IllegalArgumentException("Invalid task list ID!"));


        LocalDateTime now = LocalDateTime.now();
        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToSave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListid,UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListid,taskId);

    }
    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId,Task task) {
        if(null==task.getId()){
            throw new IllegalArgumentException("Task has no ID !");
        }
        if(!Objects.equals(taskId,task.getId())){
            throw new IllegalArgumentException("Task ID does not match!");
        }

        if(null == task.getPriority()){
            throw new IllegalArgumentException("Task priority has no value!");
        }

        if(null == task.getStatus()){
            throw new IllegalArgumentException("Task status has no value!");
        }

        Task exitingTask = taskRepository.findByTaskListIdAndId(taskListId,taskId)
                .orElseThrow(()->new IllegalArgumentException("Invalid task list ID!"));

        exitingTask.setTitle(task.getTitle());
        exitingTask.setDescription(task.getDescription());
        exitingTask.setDueDate(task.getDueDate());
        exitingTask.setPriority(task.getPriority());
        exitingTask.setStatus(task.getStatus());
        exitingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(exitingTask);
    }
    @Transactional
    @Override
    public void deleteTask(UUID taskListId,UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId,taskId);
    }
}
