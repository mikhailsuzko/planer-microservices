package com.sma.micro.planner.todo.application.use_case.task;

import com.sma.micro.planner.todo.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTaskUseCase {
    private final TaskRepository repository;

    public void execute(Long id, String userId) {
        repository.delete(id, userId);
    }
}
