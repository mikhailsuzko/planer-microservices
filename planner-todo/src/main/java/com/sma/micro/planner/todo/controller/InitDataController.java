package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.plannerentity.entity.Statistics;
import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.todo.service.CategoryService;
import com.sma.micro.planner.todo.service.PriorityService;
import com.sma.micro.planner.todo.service.StatisticsService;
import com.sma.micro.planner.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class InitDataController {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;
    private final StatisticsService statisticsService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init(@RequestBody Long userId) {
        var statistics = Statistics.builder().userId(userId).completed(0L).uncompleted(0L).build();
        statisticsService.add(statistics);

        var priorityHigh = Priority.builder().title("High").color("#FF0000").userId(userId).build();
        var priorityLow = Priority.builder().title("Low").color("#0000FF").userId(userId).build();
        var priorityMed = Priority.builder().title("Medium").color("#008000").userId(userId).build();
        priorityService.addAll(List.of(priorityHigh, priorityMed, priorityLow));

        var categoryWork = Category.builder().title("Work")
                .completedCount(0L).uncompletedCount(0L).userId(userId).build();
        var categoryHome = Category.builder().title("Home")
                .completedCount(0L).uncompletedCount(0L).userId(userId).build();
        categoryService.addAll(List.of(categoryWork, categoryHome));

        var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        var oneWeek = LocalDate.now().plusDays(7).atStartOfDay();

        var task1 = Task.builder()
                .taskDate(tomorrow)
                .priority(priorityHigh)
                .category(categoryWork)
                .title("Prepare report")
                .completed(false)
                .userId(userId)
                .build();
        var task2 = Task.builder()
                .taskDate(oneWeek)
                .priority(priorityLow)
                .category(categoryHome)
                .title("Reading")
                .completed(false)
                .userId(userId)
                .build();
        taskService.addAll(List.of(task1, task2));
        return ResponseEntity.ok(true);
    }

}
