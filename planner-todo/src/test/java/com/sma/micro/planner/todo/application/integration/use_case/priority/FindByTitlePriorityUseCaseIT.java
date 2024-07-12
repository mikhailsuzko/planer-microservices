package com.sma.micro.planner.todo.application.integration.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.FindByTitlePrioritiesUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class FindByTitlePriorityUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindByTitlePrioritiesUseCase service;

    @Test
    void findByTitle() {
        var rq = "i";

        var result = service.execute(rq, USER_ID);

        assertThat(result).hasSize(2)
                .containsExactly(
                        new PriorityPublicData(100L, "High", "#FBBABA"),
                        new PriorityPublicData(101L, "Medium", "#CFF4CF")
                );
    }

}