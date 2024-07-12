package com.sma.micro.planner.todo.application.use_case.init;

import com.sma.micro.planner.todo.application.use_case.stat.CreateStatUseCase;
import com.sma.micro.planner.todo.application.use_case.stat.FindStatUseCase;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.entity.Task;
import com.sma.micro.planner.todo.infrastructure.repository.JpaCategoryRepository;
import com.sma.micro.planner.todo.infrastructure.repository.JpaPriorityRepository;
import com.sma.micro.planner.todo.infrastructure.repository.JpaTaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = InitDataUseCase.class)
class InitDataUseCaseTest {
    @Autowired
    private InitDataUseCase service;
    @MockBean
    private JpaTaskRepository taskRepository;
    @MockBean
    private JpaPriorityRepository priorityRepository;
    @MockBean
    private JpaCategoryRepository categoryRepository;
    @MockBean
    private CreateStatUseCase createStatUseCase;
    @MockBean
    private FindStatUseCase findStatUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(createStatUseCase, findStatUseCase, priorityRepository, categoryRepository, taskRepository);
    }

    @Test
    void execute_false() {
        assertThat(service.execute(USER_ID)).isFalse();

        verify(findStatUseCase).execute(USER_ID);
    }

    @Test
    void execute_true() {
        when(findStatUseCase.execute(USER_ID)).thenThrow(NoSuchElementException.class);
        var priorityHigh = Priority.builder().title("High").color("#FBBABA").userId(USER_ID).build();
        var priorityLow = Priority.builder().title("Low").color("#CCE7FF").userId(USER_ID).build();
        var priorityMed = Priority.builder().title("Medium").color("#CFF4CF").userId(USER_ID).build();
        var categoryWork = Category.builder().title("Work").userId(USER_ID).build();
        var categoryHome = Category.builder().title("Home").userId(USER_ID).build();
        var categorySport = Category.builder().title("Sport").userId(USER_ID).build();
        var categoryTravelling = Category.builder().title("Travelling").userId(USER_ID).build();
        var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        var oneWeek = LocalDate.now().plusDays(7).atStartOfDay();
        var task1 = Task.builder()
                .taskDate(tomorrow)
                .priority(priorityHigh)
                .category(categoryWork)
                .title("Prepare report")
                .userId(USER_ID)
                .build();
        var task2 = Task.builder()
                .taskDate(oneWeek)
                .priority(priorityLow)
                .category(categoryHome)
                .title("Reading")
                .userId(USER_ID)
                .build();

        assertThat(service.execute(USER_ID)).isTrue();

        verify(createStatUseCase).execute(USER_ID);
        verify(findStatUseCase).execute(USER_ID);
        verify(priorityRepository).saveAll(List.of(priorityHigh, priorityMed, priorityLow));
        verify(categoryRepository).saveAll(List.of(categoryWork, categoryHome, categorySport, categoryTravelling));
        verify(taskRepository).saveAll(List.of(task1, task2));
    }
}