package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePriorityUseCase {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    public PriorityPublicData execute(PriorityRegistrationData data, String userId) {
        var priority = mapper.toPriority(data, userId);
        return mapper.toPriorityPublicData(repository.add(priority));
    }
}
