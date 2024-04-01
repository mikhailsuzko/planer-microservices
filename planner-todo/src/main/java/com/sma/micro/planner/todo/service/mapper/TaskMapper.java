package com.sma.micro.planner.todo.service.mapper;

import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.todo.dto.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task dtoToTask(TaskDto dto, String userId);
    TaskDto taskToDto(Task task);
}
