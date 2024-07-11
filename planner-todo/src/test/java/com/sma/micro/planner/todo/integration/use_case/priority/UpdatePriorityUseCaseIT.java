package com.sma.micro.planner.todo.integration.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.UpdatePriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;


class UpdatePriorityUseCaseIT extends IntegrationTestBase {
    @Autowired
    private UpdatePriorityUseCase service;
    @Autowired
    private PriorityRepository repository;

    @Test
    void update() {
        var id = 102L;
        var priority = new PriorityUpdateData(id, "High", "#CFF4CF");
        var result = repository.findById(id, USER_ID);
        assertThat(result.getTitle()).isNotEqualTo(priority.title());

        service.execute(priority, USER_ID);

        result = repository.findById(id, USER_ID);
        assertThat(result.getTitle()).isEqualTo(priority.title());
        assertThat(result.getColor()).isEqualTo(priority.color());
    }
}