package com.sma.micro.planner.todo.application.use_case.category.dto;

import com.sma.micro.planner.todo.domain.entity.Category;

public record CategoryPublicData(Long id,
                                 String title,
                                 Long completedCount,
                                 Long uncompletedCount) {
    public CategoryPublicData(Category category) {
        this(
                category.getId(),
                category.getTitle(),
                category.getCompletedCount(),
                category.getUncompletedCount()
        );
    }
}
