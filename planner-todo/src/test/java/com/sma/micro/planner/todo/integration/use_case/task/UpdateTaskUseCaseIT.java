package com.sma.micro.planner.todo.integration.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.UpdateTaskUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskUpdateData;
import com.sma.micro.planner.todo.domain.entity.Task;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;


class UpdateTaskUseCaseIT extends IntegrationTestBase {
    @Autowired
    private UpdateTaskUseCase service;
    @Autowired
    private TaskRepository repository;

    @Test
    void update() {
        var id = 100L;
        var task = new TaskUpdateData(id, "Hobby", false, null, CATEGORY_PUBLIC_DATA_101, PRIORITY_PUBLIC_DATA_102);
        var result = repository.findById(id, USER_ID);
        assertThat(result.getTitle()).isNotEqualTo(task.title());

        service.execute(task, USER_ID);

        result = repository.findById(id, USER_ID);
        var expected = new Task(id, "Hobby", false, null, CATEGORY_101, PRIORITY_102, USER_ID);
        assertThat(result).isEqualTo(expected);
    }
}