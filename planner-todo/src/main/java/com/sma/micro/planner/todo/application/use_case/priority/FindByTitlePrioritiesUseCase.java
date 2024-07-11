package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByTitlePrioritiesUseCase {
    private final PriorityRepository repository;
    private final PriorityMapper mapper;

    public List<PriorityPublicData> execute(String title, String userId) {
        return repository.findByTitle(Utils.prepareParam(title), userId).stream()
                .map(mapper::toPriorityPublicData)
                .toList();
    }
}
