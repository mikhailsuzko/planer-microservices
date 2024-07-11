package com.sma.micro.planner.todo.application.use_case.stat;

import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.domain.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStatUseCase {
    private final StatRepository repository;

    public void execute(String userId) {
        var stat = Stat.builder().userId(userId).build();
        repository.add(stat);
    }
}
