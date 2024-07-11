package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePriorityUseCase {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    public void execute(PriorityUpdateData data, String userId) {
        var priority = mapper.toPriority(data, userId);
        repository.update(priority);
    }
}
