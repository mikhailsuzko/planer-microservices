package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskRegistrationData;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTaskUseCase {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskPublicData execute(TaskRegistrationData data, String userId) {
        var task = mapper.toTask(data, userId);
        return mapper.toTaskPublicData(repository.add(task));
    }
}
