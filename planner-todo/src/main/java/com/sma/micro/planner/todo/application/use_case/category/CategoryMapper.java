package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.domain.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public CategoryPublicData toCategoryPublicData(Category category) {
        return new CategoryPublicData(
                category.getId(),
                category.getTitle(),
                category.getCompletedCount(),
                category.getUncompletedCount());
    }

    public Category toCategory(CategoryPublicData data, String userId) {
        return new Category(
                data.id(),
                data.title(),
                data.completedCount(),
                data.uncompletedCount(),
                userId);
    }

    public Category toCategory(CategoryRegistrationData data, String userId) {
        return new Category(
                data.title(),
                userId);
    }

    public Category toCategory(CategoryUpdateData data, String userId) {
        return new Category(
                data.id(),
                data.title(),
                userId);
    }
}
