package com.sma.micro.planner.todo.domain.repository;

import com.sma.micro.planner.todo.domain.entity.Category;

import java.util.List;

public interface CategoryRepository {
    Category add(Category category);

    void update(Category category);

    void delete(Long id, String userId);

    Category findById(Long id, String userId);

    List<Category> findAll(String userId);

    List<Category> findByTitle(String title, String userId);

}
