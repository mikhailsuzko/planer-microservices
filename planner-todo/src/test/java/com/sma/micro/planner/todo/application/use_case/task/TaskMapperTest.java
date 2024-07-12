package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.category.CategoryMapper;
import com.sma.micro.planner.todo.application.use_case.priority.PriorityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {TaskMapper.class, PriorityMapper.class, CategoryMapper.class})
class TaskMapperTest {
    @Autowired
    private TaskMapper mapper;


    @Test
    void toTaskPublicData() {
        var result = mapper.toTaskPublicData(TASK_100);

        assertThat(result).isEqualTo(TASK_PUBLIC_DATA_100);
    }

    @Test
    void taskPublicDataToCategory() {
        var result = mapper.toTask(TASK_PUBLIC_DATA_100, USER_ID);

        assertThat(result).isEqualTo(TASK_100);
    }

    @Test
    void taskRegistrationDataToCategory() {
        var result = mapper.toTask(TASK_REGISTRATION_DATA, USER_ID);

        assertThat(result).isEqualTo(TASK);
    }

    @Test
    void taskUpdateDataToCategory() {
        var result = mapper.toTask(TASK_UPDATE_DATA_100, USER_ID);

        assertThat(result).isEqualTo(TASK_100);
    }
}
