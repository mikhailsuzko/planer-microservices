package com.sma.micro.planner.todo.application.integration.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.DeletePriorityUseCase;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DeletePriorityUseCaseIT extends IntegrationTestBase {
    @Autowired
    private DeletePriorityUseCase service;
    @Autowired
    private PriorityRepository repository;

    @Test
    void deleteById() {
        var id = 102L;
        assertDoesNotThrow(() -> repository.findById(id, USER_ID));

        service.execute(id, USER_ID);

        assertThrows(NoSuchElementException.class, () -> repository.findById(id, USER_ID));
    }

}