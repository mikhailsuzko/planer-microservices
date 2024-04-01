package com.sma.micro.planner.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record PriorityDto(Long id,
                          @NotBlank(message = "Field 'title' can't be blank")
                          String title,
                          String color) {
}
