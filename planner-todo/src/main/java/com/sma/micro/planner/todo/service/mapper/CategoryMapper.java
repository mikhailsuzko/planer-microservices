package com.sma.micro.planner.todo.service.mapper;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.todo.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category dtoToCategory(CategoryDto dto, String userId);
    CategoryDto categoryToDto(Category category);
}
