package com.sma.micro.planner.todo.db.mapper;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.todo.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto dto, String userId);

    CategoryDto toDomain(Category category);
}
