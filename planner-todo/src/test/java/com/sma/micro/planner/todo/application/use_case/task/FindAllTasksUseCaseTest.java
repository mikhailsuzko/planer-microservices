package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.category.CategoryMapper;
import com.sma.micro.planner.todo.application.use_case.priority.PriorityMapper;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FindAllTasksUseCase.class, TaskMapper.class, CategoryMapper.class, PriorityMapper.class})
class FindAllTasksUseCaseTest {
    @Autowired
    private FindAllTasksUseCase findAllTasksUseCase;
    @MockBean
    private TaskRepository taskRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void execute() {
        when(taskRepository.findAll(USER_ID)).thenReturn(List.of(TASK_100, TASK_101));

        var result = findAllTasksUseCase.execute(USER_ID);

        assertThat(result).containsExactly(TASK_PUBLIC_DATA_100, TASK_PUBLIC_DATA_101);
        verify(taskRepository).findAll(USER_ID);
    }
}