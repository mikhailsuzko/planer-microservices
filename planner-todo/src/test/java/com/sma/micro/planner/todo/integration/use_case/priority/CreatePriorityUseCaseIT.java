package com.sma.micro.planner.todo.integration.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.CreatePriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;


class CreatePriorityUseCaseIT extends IntegrationTestBase {
    @Autowired
    private CreatePriorityUseCase service;

    @Test
    void add() {
        var userId = UUID.randomUUID().toString();
        var rq = new PriorityRegistrationData(TITLE, COLOR);

        var result = service.execute(rq, userId);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(new PriorityPublicData(ID_10, TITLE, COLOR));
    }
}