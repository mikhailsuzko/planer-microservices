package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.plannerentity.entity.Stat;
import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.todo.db.jpa.CategoryJpaRepository;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.dto.StatDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = InitDataService.class)
class InitDataServiceTest {
    @Autowired
    private InitDataService service;
    @MockBean
    private TaskService taskService;
    @MockBean
    private PriorityService priorityService;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategoryJpaRepository categoryJpaRepository;
    @MockBean
    private StatService statService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(statService, categoryService, priorityService, taskService, categoryJpaRepository);
    }

    @Test
    void init_false() {
        when(statService.findStat(USER_ID)).thenReturn(new StatDto(ID, COUNT, COUNT));

        assertThat(service.init(USER_ID)).isFalse();

        verify(statService).findStat(USER_ID);
    }

    @Test
    void init_true() {
        when(statService.findStat(USER_ID)).thenThrow(NoSuchElementException.class);
        var categoryWork = Category.builder().title("Work").userId(USER_ID).build();
        var categoryHome = Category.builder().title("Home").userId(USER_ID).build();
        when(categoryJpaRepository.findByTitle("%work%", USER_ID)).thenReturn(List.of(categoryWork));
        when(categoryJpaRepository.findByTitle("%home%", USER_ID)).thenReturn(List.of(categoryHome));
        var stat = Stat.builder().userId(USER_ID).build();
        var priorityHigh = Priority.builder().title("High").color("#FBBABA").userId(USER_ID).build();
        var priorityLow = Priority.builder().title("Low").color("#CCE7FF").userId(USER_ID).build();
        var priorityMed = Priority.builder().title("Medium").color("#CFF4CF").userId(USER_ID).build();
        var categoryWorkDto = new CategoryDto("Work");
        var categoryHomeDto = new CategoryDto("Home");
        var categorySportDto = new CategoryDto("Sport");
        var categoryTravellingDto = new CategoryDto("Travelling");
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

        assertThat(service.init(USER_ID)).isTrue();

        verify(statService).findStat(USER_ID);
        verify(statService).add(stat);
        verify(priorityService).addAll(List.of(priorityHigh, priorityMed, priorityLow));
        verify(categoryService).addAll(List.of(categoryWorkDto, categoryHomeDto, categorySportDto, categoryTravellingDto), USER_ID);
        verify(taskService).addAll(List.of(task1, task2));
        verify(categoryJpaRepository).findByTitle("%work%", USER_ID);
        verify(categoryJpaRepository).findByTitle("%home%", USER_ID);
    }
}