package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindTaskByIdUseCase {
    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskPublicData execute(Long id, String userId) {
        return mapper.toTaskPublicData(repository.findById(id, userId));
    }
}
