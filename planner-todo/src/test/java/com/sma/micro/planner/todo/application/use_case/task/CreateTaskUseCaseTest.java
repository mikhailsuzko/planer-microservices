package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.category.CategoryMapper;
import com.sma.micro.planner.todo.application.use_case.priority.PriorityMapper;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CreateTaskUseCase.class, TaskMapper.class, CategoryMapper.class, PriorityMapper.class})
class CreateTaskUseCaseTest {
    @Autowired
    private CreateTaskUseCase createTaskUseCase;
    @MockBean
    private TaskRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void execute() {
        when(repository.add(TASK)).thenReturn(TASK_100);

        var result = createTaskUseCase.execute(TASK_REGISTRATION_DATA, USER_ID);

        assertThat(result).isEqualTo(TASK_PUBLIC_DATA_100);
        verify(repository).add(TASK);
    }

}