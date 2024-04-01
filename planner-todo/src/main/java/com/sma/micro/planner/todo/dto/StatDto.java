package com.sma.micro.planner.todo.dto;

public record StatDto(Long id,
                      Long completedTotal,
                      Long uncompletedTotal) {
}
