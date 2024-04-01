package com.sma.micro.planner.todo.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskDto(Long id,
                      @NotBlank(message = "Field 'title' can't be blank")
                      String title,
                      Boolean completed,
                      LocalDateTime taskDate,
                      CategoryDto category,
                      PriorityDto priority) {
}
