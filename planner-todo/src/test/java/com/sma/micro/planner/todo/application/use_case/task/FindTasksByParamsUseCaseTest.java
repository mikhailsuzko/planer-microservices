package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.category.CategoryMapper;
import com.sma.micro.planner.todo.application.use_case.priority.PriorityMapper;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskSearchValues;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FindTasksByParamsUseCase.class, TaskMapper.class, CategoryMapper.class, PriorityMapper.class})
class FindTasksByParamsUseCaseTest {
    @Autowired
    private FindTasksByParamsUseCase findTasksByParamsUseCase;
    @MockBean
    private TaskRepository taskRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void execute() {
        var sqlTitle = "%" + TITLE + "%";
        var sort = Sort.by(Sort.Direction.ASC, TITLE);
        var request = PageRequest.of(0, 5, sort);
        var pages = new PageImpl<>(List.of(TASK_100), request, 10);
        when(taskRepository.findByParams(
                sqlTitle, false, 100L, 100L, null, null, USER_ID, request))
                .thenReturn(pages);
        var params = new TaskSearchValues(TITLE, false, 100L, 100L, null, null,
                0, 5, TITLE, "ASC");

        var result = findTasksByParamsUseCase.execute(params, USER_ID);

        var pagesExpected = new PageImpl<>(List.of(TASK_PUBLIC_DATA_100), request, 10);
        assertThat(result).containsExactly(TASK_PUBLIC_DATA_100);
        verify(taskRepository).findByParams(
                sqlTitle, false, 100L, 100L, null, null, USER_ID, request);
        assertThat(result).isEqualTo(pagesExpected);
    }
}