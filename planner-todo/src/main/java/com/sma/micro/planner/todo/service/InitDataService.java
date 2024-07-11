package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.application.use_case.stat.CreateStatUseCase;
import com.sma.micro.planner.todo.application.use_case.stat.FindStatUseCase;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.entity.Task;
import com.sma.micro.planner.todo.infrastructure.repository.JpaCategoryRepository;
import com.sma.micro.planner.todo.infrastructure.repository.JpaPriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InitDataService {
    private final TaskService taskService;
    private final JpaPriorityRepository priorityRepository;
    private final CreateStatUseCase createStatUseCase;
    private final FindStatUseCase findStatUseCase;
    private final JpaCategoryRepository categoryRepository;

    public boolean init(String userId) {
        try {
            findStatUseCase.execute(userId);
            return false;
        } catch (NoSuchElementException e) {
            createStatUseCase.execute(userId);

            var priorityHigh = Priority.builder().title("High").color("#FBBABA").userId(userId).build();
            var priorityLow = Priority.builder().title("Low").color("#CCE7FF").userId(userId).build();
            var priorityMed = Priority.builder().title("Medium").color("#CFF4CF").userId(userId).build();
            priorityRepository.saveAll(List.of(priorityHigh, priorityMed, priorityLow));

            var categoryWork = Category.builder().title("Work").userId(userId).build();
            var categoryHome = Category.builder().title("Home").userId(userId).build();
            var categorySport = Category.builder().title("Sport").userId(userId).build();
            var categoryTravelling = Category.builder().title("Travelling").userId(userId).build();
            categoryRepository.saveAll(List.of(categoryWork, categoryHome, categorySport, categoryTravelling));

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
