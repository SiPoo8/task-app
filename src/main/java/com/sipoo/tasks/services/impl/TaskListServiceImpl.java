package com.sipoo.tasks.services.impl;

import com.sipoo.tasks.entities.TaskList;
import com.sipoo.tasks.repositories.TaskListRepository;
import com.sipoo.tasks.services.TaskListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (null!=taskList.getId()) {
            throw new IllegalArgumentException("Task list already has an ID!");
        }

        if(null == taskList.getTitle() || taskList.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task list title is required!");
        }

        LocalDateTime now = LocalDateTime.now();

        return taskListRepository.save(new TaskList(
                null,
                taskList.getTitle(),
                taskList.getDescription(),
                null,
                now,
                now

        ));

    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepository.findById(id);
    }

    @Override
    public TaskList updateTaskList(UUID taskListid, TaskList taskList) {
        if(null==taskList.getId()) {
            throw new IllegalArgumentException("Task list id is required!");
        }

        if(!Objects.equals(taskList.getId(),taskListid)){
            throw new IllegalArgumentException("Task list id does not match!");
        }

        TaskList existingTaskList = taskListRepository.findById(taskListid)
                .orElseThrow(() -> new IllegalArgumentException("Task list id does not exist!"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID taskListid) {
        taskListRepository.deleteById(taskListid);
    }
}
