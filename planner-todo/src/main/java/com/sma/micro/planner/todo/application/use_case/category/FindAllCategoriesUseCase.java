package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllCategoriesUseCase {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryPublicData> execute(String userId) {
        return repository.findAll(userId).stream()
                .map(mapper::toCategoryPublicData)
                .toList();
    }
}
