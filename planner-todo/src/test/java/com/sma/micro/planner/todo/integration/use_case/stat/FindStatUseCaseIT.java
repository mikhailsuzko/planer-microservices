package com.sma.micro.planner.todo.integration.use_case.stat;

import com.sma.micro.planner.todo.application.use_case.stat.FindStatUseCase;
import com.sma.micro.planner.todo.application.use_case.stat.dto.StatPublicData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FindStatUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindStatUseCase service;

    @Test
    void find() {
        var result = service.execute(USER_ID);

        assertThat(result).isEqualTo(new StatPublicData(2L, 3L));
    }

    @Test
    void find_exception() {
        assertThrows(NoSuchElementException.class, () -> service.execute("NoUserId"));
    }
}