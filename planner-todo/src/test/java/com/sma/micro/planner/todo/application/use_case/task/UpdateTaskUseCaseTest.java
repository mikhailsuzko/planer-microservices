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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {UpdateTaskUseCase.class, TaskMapper.class, CategoryMapper.class, PriorityMapper.class})
class UpdateTaskUseCaseTest {
    @Autowired
    private UpdateTaskUseCase updateTaskUseCase;
    @MockBean
    private TaskRepository taskRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void execute() {
        updateTaskUseCase.execute(TASK_UPDATE_DATA_100, USER_ID);

        verify(taskRepository).update(TASK_100);
    }
}