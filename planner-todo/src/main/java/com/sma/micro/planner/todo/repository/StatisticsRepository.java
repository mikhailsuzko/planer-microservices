package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.plannerentity.entity.Statistics;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatisticsRepository extends CrudRepository<Statistics, Long> {

    Optional<Statistics> findByUserId(String userId);
}
