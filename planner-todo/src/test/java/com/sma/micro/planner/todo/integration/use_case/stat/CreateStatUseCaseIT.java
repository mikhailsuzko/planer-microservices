package com.sma.micro.planner.todo.integration.use_case.stat;

import com.sma.micro.planner.todo.application.use_case.stat.CreateStatUseCase;
import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.infrastructure.repository.JpaStatRepository;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CreateStatUseCaseIT extends IntegrationTestBase {
    @Autowired
    private CreateStatUseCase service;
    @Autowired
    private JpaStatRepository repository;

    @Test
    void add() {
        var userId = UUID.randomUUID().toString();

        service.execute(userId);

        var result = repository.find(userId);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(new Stat(null, 0L, 0L, userId));
    }
}