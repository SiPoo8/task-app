package com.sipoo.tasks.dto;

import com.sipoo.tasks.entities.TaskPriority;
import com.sipoo.tasks.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {



}
