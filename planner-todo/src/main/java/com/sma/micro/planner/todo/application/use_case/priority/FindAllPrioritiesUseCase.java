package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllPrioritiesUseCase {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    public List<PriorityPublicData> execute(String userId) {
        return repository.findAll(userId).stream()
                .map(mapper::toPriorityPublicData)
                .toList();
    }
}
