package com.sma.micro.planner.todo.integration.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.FindAllPrioritiesUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;


class FindAllPriorityUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindAllPrioritiesUseCase service;

    @Test
    void findAll() {
        var result = service.execute(USER_ID);

        assertThat(result).hasSize(3)
                .containsExactlyInAnyOrder(
                        new PriorityPublicData(100L, "High", "#FBBABA"),
                        new PriorityPublicData(101L, "Medium", "#CFF4CF"),
                        new PriorityPublicData(102L, "Low", "#CCE7FF")
                );
    }


}