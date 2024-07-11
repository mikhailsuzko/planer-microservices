package com.sma.micro.planner.todo.application.use_case.stat;

import com.sma.micro.planner.todo.application.use_case.stat.dto.StatPublicData;
import com.sma.micro.planner.todo.domain.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindStatUseCase {
    private final StatRepository repository;

    public StatPublicData execute(String userId) {
        return new StatPublicData(repository.find(userId));
    }
}
