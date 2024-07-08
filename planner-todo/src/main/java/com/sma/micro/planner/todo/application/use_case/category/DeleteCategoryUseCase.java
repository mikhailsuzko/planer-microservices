package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryUseCase {
    private final CategoryRepository repository;

    public void execute(Long id, String userId) {
        repository.delete(id, userId);
    }
}
