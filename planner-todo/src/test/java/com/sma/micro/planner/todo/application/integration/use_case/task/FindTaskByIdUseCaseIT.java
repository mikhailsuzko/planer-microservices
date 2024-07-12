package com.sma.micro.planner.todo.application.integration.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.FindTaskByIdUseCase;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FindTaskByIdUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindTaskByIdUseCase service;

    @Test
    void findById() {
        var result = service.execute(100L, USER_ID);

        assertThat(result).isEqualTo(TASK_PUBLIC_DATA_100);
    }

    @Test
    void findById_exception() {
        assertThrows(NoSuchElementException.class, () -> service.execute(ID_10, USER_ID));
    }
}