package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdPriorityUseCase {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    public PriorityPublicData execute(Long id, String userId) {
        return mapper.toPriorityPublicData(repository.findById(id, userId));
    }
}
