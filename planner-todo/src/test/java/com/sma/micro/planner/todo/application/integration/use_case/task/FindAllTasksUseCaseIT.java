package com.sma.micro.planner.todo.application.integration.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.FindAllTasksUseCase;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;


class FindAllTasksUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindAllTasksUseCase service;

    @Test
    void findAll() {
        var result = service.execute(USER_ID);

        assertThat(result).hasSize(5)
                .containsExactlyInAnyOrder(
                        TASK_PUBLIC_DATA_100,
                        TASK_PUBLIC_DATA_101,
                        TASK_PUBLIC_DATA_102,
                        TASK_PUBLIC_DATA_103,
                        TASK_PUBLIC_DATA_104
                );
    }
}