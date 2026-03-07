package com.sipoo.tasks.controller;

import com.sipoo.tasks.dto.TaskListDto;
import com.sipoo.tasks.entities.TaskList;
import com.sipoo.tasks.mappers.TaskListMapper;
import com.sipoo.tasks.services.TaskListService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
@AllArgsConstructor
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;


    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();

    }
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        TaskList createdTaskList = taskListService.createTaskList(taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(createdTaskList);
    }
    @GetMapping(path = "/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListId) {
        return taskListService.getTaskList(taskListId).map(taskListMapper::toDto);
    }
    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable ("task_list_id") UUID taskListId,
                                      @RequestBody TaskListDto taskListDto ) {
        TaskList updatedTaskList = taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(updatedTaskList);
    }


}
