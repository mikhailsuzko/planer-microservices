package com.sma.micro.planner.todo.domain.repository;

import com.sma.micro.planner.todo.domain.entity.Priority;

import java.util.List;

public interface PriorityRepository {
    Priority add(Priority priority);

    void update(Priority priority);

    void delete(Long id, String userId);

    Priority findById(Long id, String userId);

    List<Priority> findAll(String userId);

    List<Priority> findByTitle(String title, String userId);

}
