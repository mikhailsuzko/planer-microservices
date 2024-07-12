package com.sma.micro.planner.todo.domain.repository;

import com.sma.micro.planner.todo.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {
    Task add(Task task);

    void update(Task task);

    void delete(Long id, String userId);

    Task findById(Long id, String userId);

    List<Task> findAll(String userId);

    Page<Task> findByParams(String title,
                            Boolean completed,
                            Long priorityId,
                            Long categoryId,
                            LocalDateTime dateFrom,
                            LocalDateTime dateTo,
                            String userId,
                            Pageable pageable);

}
