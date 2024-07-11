package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePriorityUseCase {
    private final PriorityRepository repository;

    public void execute(Long id, String userId) {
        repository.delete(id, userId);
    }
}
