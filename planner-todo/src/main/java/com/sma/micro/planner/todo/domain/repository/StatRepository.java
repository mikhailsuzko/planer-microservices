package com.sma.micro.planner.todo.domain.repository;

import com.sma.micro.planner.todo.domain.entity.Stat;

public interface StatRepository {
    Stat add(Stat stat);

    Stat find(String userId);
}
