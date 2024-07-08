package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByTitleCategoriesUseCase {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryPublicData> execute(String title, String userId) {
        return repository.findByTitle(Utils.prepareParam(title), userId).stream()
                .map(mapper::toCategoryPublicData)
                .toList();
    }
}
