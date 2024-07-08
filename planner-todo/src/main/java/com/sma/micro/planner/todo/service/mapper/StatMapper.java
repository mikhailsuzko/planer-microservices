package com.sma.micro.planner.todo.service.mapper;

import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.dto.StatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatMapper {

    StatDto statToDto(Stat category);
}
