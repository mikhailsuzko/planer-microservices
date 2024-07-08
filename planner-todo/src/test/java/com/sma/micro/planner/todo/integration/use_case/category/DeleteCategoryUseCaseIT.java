package com.sma.micro.planner.todo.integration.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.DeleteCategoryUseCase;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeleteCategoryUseCaseIT extends IntegrationTestBase {
    @Autowired
    private DeleteCategoryUseCase service;
    @Autowired
    private CategoryRepository repository;

    @Test
    void deleteById() {
        var id = 102L;
        assertDoesNotThrow(() -> repository.findById(id, USER_ID));

        service.execute(id, USER_ID);

        assertThrows(NoSuchElementException.class, () -> repository.findById(id, USER_ID));
    }

}