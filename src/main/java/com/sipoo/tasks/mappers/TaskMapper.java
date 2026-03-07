package com.sipoo.tasks.mappers;

import com.sipoo.tasks.dto.TaskDto;
import com.sipoo.tasks.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
