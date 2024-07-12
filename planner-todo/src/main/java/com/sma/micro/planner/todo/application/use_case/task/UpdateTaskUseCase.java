package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.dto.TaskUpdateData;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTaskUseCase {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public void execute(TaskUpdateData data, String userId) {
        var task = mapper.toTask(data, userId);
        repository.update(task);
    }
}
