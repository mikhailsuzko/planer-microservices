package com.sma.micro.planner.todo.application.use_case.priority.dto;

import com.sma.micro.planner.todo.domain.entity.Priority;

public record PriorityPublicData(Long id,
                                 String title,
                                 String color) {
    public PriorityPublicData(Priority priority) {
        this(
                priority.getId(),
                priority.getTitle(),
                priority.getColor()
        );
    }
}
