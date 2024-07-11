package com.sma.micro.planner.todo.application.use_case.priority.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PriorityRegistrationData(
        @NotBlank(message = "title must not be blank")
        @NotNull(message = "title must not be null")
        String title,
        @NotBlank(message = "color must not be blank")
        @NotNull(message = "color must not be null")
        String color) {
}
