package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCategoryUseCase {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public void execute(CategoryUpdateData data, String userId) {
        var category = mapper.toCategory(data, userId);
        repository.update(category);
    }
}
