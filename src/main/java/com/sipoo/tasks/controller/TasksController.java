package com.sipoo.tasks.controller;

import com.sipoo.tasks.dto.TaskDto;
import com.sipoo.tasks.entities.Task;
import com.sipoo.tasks.mappers.TaskMapper;
import com.sipoo.tasks.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists/{task_list_id}/tasks")
@AllArgsConstructor
public class TasksController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;


    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId,
                              @RequestBody TaskDto taskDto) {
        Task createdTask = taskService.createTask(taskListId,taskMapper.fromDto(taskDto));
        return taskMapper.toDto(createdTask);
    }

    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_list_id") UUID TaskListId,
                                     @PathVariable("task_id") UUID taskId) {
        return taskService.getTask(TaskListId,taskId).map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(@PathVariable("task_list_id") UUID TaskListId,
                              @PathVariable("task_id") UUID taskId,
                              @RequestBody TaskDto taskDto){

        Task updatedTask = taskService.updateTask(TaskListId,taskId,taskMapper.fromDto(taskDto));

        return taskMapper.toDto(updatedTask);

    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID TaskListId,
                           @PathVariable("task_id") UUID taskId){
        taskService.deleteTask(TaskListId,taskId);

    }


}
