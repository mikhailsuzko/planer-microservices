package com.sma.micro.planner.todo.integration.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.CreateTaskUseCase;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.sma.micro.planner.todo.model.Constants.TASK_PUBLIC_DATA_100;
import static com.sma.micro.planner.todo.model.Constants.TASK_REGISTRATION_DATA;
import static org.assertj.core.api.Assertions.assertThat;


class CreateTaskUseCaseIT extends IntegrationTestBase {
    @Autowired
    private CreateTaskUseCase service;

    @Test
    void add() {
        var userId = UUID.randomUUID().toString();

        var result = service.execute(TASK_REGISTRATION_DATA, userId);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(TASK_PUBLIC_DATA_100);
    }
}