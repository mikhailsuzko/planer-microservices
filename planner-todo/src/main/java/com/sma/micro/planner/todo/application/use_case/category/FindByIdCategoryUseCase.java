package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdCategoryUseCase {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryPublicData execute(Long id, String userId) {
        return mapper.toCategoryPublicData(repository.findById(id, userId));
    }
}
