package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllTasksUseCase {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public List<TaskPublicData> execute(String userId) {
        return repository.findAll(userId).stream()
                .map(mapper::toTaskPublicData)
                .toList();
    }
}
