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

@SpringBootTest(classes = {FindTaskByIdUseCase.class, TaskMapper.class, CategoryMapper.class, PriorityMapper.class})
class FindTaskByIdUseCaseTest {
    @Autowired
    private FindTaskByIdUseCase findTaskUseCase;
    @MockBean
    private TaskRepository taskRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void execute() {
        when(taskRepository.findById(ID_10, USER_ID)).thenReturn(TASK_100);

        var result = findTaskUseCase.execute(ID_10, USER_ID);

        verify(taskRepository).findById(ID_10, USER_ID);
        assertThat(result).isEqualTo(TASK_PUBLIC_DATA_100);
    }
}