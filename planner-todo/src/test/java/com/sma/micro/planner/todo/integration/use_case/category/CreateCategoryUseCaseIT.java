package com.sma.micro.planner.todo.integration.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.CreateCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;


class CreateCategoryUseCaseIT extends IntegrationTestBase {
    @Autowired
    private CreateCategoryUseCase service;

    @Test
    void add() {
        var userId = UUID.randomUUID().toString();
        var rq = new CategoryRegistrationData(TITLE);

        var result = service.execute(rq, userId);

        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(new CategoryPublicData(ID_10, TITLE, COUNT, COUNT));
    }
}