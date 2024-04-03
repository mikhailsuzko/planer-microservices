package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.plannerentity.entity.Stat;
import com.sma.micro.planner.plannerentity.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InitDataService {
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;
    private final StatService statService;

    public boolean init(String userId) {
        try {
            statService.findStat(userId);
            return false;
        } catch (NoSuchElementException e) {
            var statistics = Stat.builder().userId(userId).build();
            statService.add(statistics);

            var priorityHigh = Priority.builder().title("High").color("#FBBABA").userId(userId).build();
            var priorityLow = Priority.builder().title("Low").color("#CCE7FF").userId(userId).build();
            var priorityMed = Priority.builder().title("Medium").color("#CFF4CF").userId(userId).build();
            priorityService.addAll(List.of(priorityHigh, priorityMed, priorityLow));

            var categoryWork = Category.builder().title("Work").userId(userId).build();
            var categoryHome = Category.builder().title("Home").userId(userId).build();
            var categorySport = Category.builder().title("Sport").userId(userId).build();
            var categoryTravelling = Category.builder().title("Travelling").userId(userId).build();
            categoryService.addAll(List.of(categoryWork, categoryHome, categorySport, categoryTravelling));

            var tomorrow = LocalDate.now().plusDays(1).atStartOfDay();
            var oneWeek = LocalDate.now().plusDays(7).atStartOfDay();

            var task1 = Task.builder()
                    .taskDate(tomorrow)
                    .priority(priorityHigh)
                    .category(categoryWork)
                    .title("Prepare report")
                    .userId(userId)
                    .build();
            var task2 = Task.builder()
                    .taskDate(oneWeek)
                    .priority(priorityLow)
                    .category(categoryHome)
                    .title("Reading")
                    .userId(userId)
                    .build();
            taskService.addAll(List.of(task1, task2));
            return true;
        }
    }

}
