package com.sma.micro.planner.todo.application.use_case.task.dto;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRegistrationData(
        @NotBlank(message = "title must not be blank")
        @NotNull(message = "title must not be null")
        String title,
        Boolean completed,
        LocalDateTime taskDate,
        CategoryPublicData category,
        PriorityPublicData priority) {
}
