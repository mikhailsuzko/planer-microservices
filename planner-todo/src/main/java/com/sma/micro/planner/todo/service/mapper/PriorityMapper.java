package com.sma.micro.planner.todo.service.mapper;

import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.todo.dto.PriorityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriorityMapper {

    Priority dtoToPriority(PriorityDto dto, String userId);
    PriorityDto priorityToDto(Priority category);
}
