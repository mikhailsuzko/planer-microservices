package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.todo.dto.CategoryDto;

import java.util.List;

public interface CategoryRepository {

    List<CategoryDto> findByUserIdOrderByTitle(String userId);

    List<CategoryDto> findByTitle(String title, String userId);

    void deleteByIdAndUserId(Long id, String userId);

    CategoryDto findByIdAndUserId(Long id, String userId);

    CategoryDto save(CategoryDto category, String userId);

    void saveAll(List<CategoryDto> categories, String userId);
}
