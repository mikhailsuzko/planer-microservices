package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.plannerentity.entity.Statistics;
import com.sma.micro.planner.todo.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository repository;

    public Statistics findStatistics(String userId) {
        return repository.findByUserId(userId).orElseThrow();
    }

    public Statistics add(Statistics statistics) {
        return repository.save(statistics);
    }
}
