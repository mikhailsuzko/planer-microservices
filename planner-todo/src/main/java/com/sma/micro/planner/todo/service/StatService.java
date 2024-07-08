package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.dto.StatDto;
import com.sma.micro.planner.todo.repository.StatRepository;
import com.sma.micro.planner.todo.service.mapper.StatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository repository;
    private final StatMapper mapper;

    public StatDto findStat(String userId) {
        var stat = repository.findByUserId(userId).orElseThrow();
        return mapper.statToDto(stat);
    }

    public Stat add(Stat stat) {
        return repository.save(stat);
    }
}
