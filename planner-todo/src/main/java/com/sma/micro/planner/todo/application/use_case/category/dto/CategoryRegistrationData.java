package com.sma.micro.planner.todo.application.use_case.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRegistrationData(
        @NotBlank(message = "title must not be blank")
        @NotNull(message = "title must not be null")
        String title) {
}
