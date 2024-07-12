package com.sma.micro.planner.todo.application.use_case.task.dto;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;

import java.time.LocalDateTime;

public record TaskPublicData(Long id,
                             String title,
                             Boolean completed,
                             LocalDateTime taskDate,
                             CategoryPublicData category,
                             PriorityPublicData priority) {

}
