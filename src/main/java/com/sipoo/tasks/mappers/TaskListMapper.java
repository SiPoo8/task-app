package com.sipoo.tasks.mappers;

import com.sipoo.tasks.dto.TaskListDto;
import com.sipoo.tasks.entities.TaskList;

public interface TaskListMapper {

    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);

}
