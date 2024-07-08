package com.sma.micro.planner.todo.application.use_case.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateData(
        @NotNull(message = "id must not be null")
        Long id,
        @NotBlank(message = "title must not be blank")
        @NotNull(message = "title must not be null")
        String title) {
}
