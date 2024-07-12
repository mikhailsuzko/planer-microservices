package com.sma.micro.planner.todo.integration.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.FindTasksByParamsUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskSearchValues;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class FindTasksByParamsUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindTasksByParamsUseCase service;

    @Test
    void findByParams() {
        var searchValues = new TaskSearchValues("i", null, null, null, null, null, 0, 5, "id", "asc");

        var result = service.execute(searchValues, USER_ID);

        assertThat(result.getContent()).hasSize(2)
                .containsExactly(TASK_PUBLIC_DATA_102, TASK_PUBLIC_DATA_104);
    }

}