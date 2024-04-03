package com.sma.micro.planner.todo.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TaskMapperImpl.class)
class TaskMapperTest {
    @Autowired
    private TaskMapper mapper;

    @Test
    void dtoToTask() {
        var result = mapper.dtoToTask(TASK_DTO, USER_ID);

        assertThat(result).isEqualTo(TASK);
    }

    @Test
    void taskToDto() {
        var result = mapper.taskToDto(TASK);

        assertThat(result).isEqualTo(TASK_DTO);
    }
}