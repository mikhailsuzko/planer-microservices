package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public Task findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Task> findAll(String userId) {
        return repository.findByUserIdOrderByTaskDateDescTitleAsc(userId);
    }

    public Task add(Task task) {
        return repository.save(task);
    }

    public void update(Task task) {
        repository.save(task);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<Task> findByParams(String title,
                                   Boolean completed,
                                   Long priorityId,
                                   Long categoryId,
                                   LocalDateTime dateFrom,
                                   LocalDateTime dateTo,
                                   String userId, Pageable pageable) {
        return repository.findByParams(Utils.prepareParam(title), completed, priorityId, categoryId,
                dateFrom, dateTo, userId, pageable);
    }

    public void addAll(List<Task> tasks) {
        repository.saveAll(tasks);
    }
}
