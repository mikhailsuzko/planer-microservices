package com.sma.micro.planner.todo.repository;

import com.sma.micro.planner.plannerentity.entity.Stat;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StatRepository extends CrudRepository<Stat, Long> {

    Optional<Stat> findByUserId(String userId);
}
