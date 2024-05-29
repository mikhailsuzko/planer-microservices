package com.sma.micro.planner.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(Long id,
                          @NotBlank(message = "Field 'title' can't be blank")
                          String title,
                          Long completedCount,
                          Long uncompletedCount) {
    public CategoryDto(String title) {
        this(null, title, 0L, 0L);
    }
}
