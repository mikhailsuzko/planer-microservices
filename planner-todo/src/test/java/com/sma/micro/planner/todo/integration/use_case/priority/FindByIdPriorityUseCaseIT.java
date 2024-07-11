package com.sma.micro.planner.todo.integration.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.FindByIdPriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.model.Constants.ID_10;
import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FindByIdPriorityUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindByIdPriorityUseCase service;

    @Test
    void findById() {
        var result = service.execute(100L, USER_ID);

        assertThat(result).isEqualTo(new PriorityPublicData(100L, "High", "#FBBABA"));
    }

    @Test
    void findById_exception() {
        assertThrows(NoSuchElementException.class, () -> service.execute(ID_10, USER_ID));
    }
}