package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.repository.PriorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PriorityService {
    private final PriorityRepository repository;

    public Priority findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Priority> findAll(Long userId) {
        return repository.findByUserIdOrderByIdAsc(userId);
    }

    public Priority add(Priority priority) {
        return repository.save(priority);
    }

    public void update(Priority priority) {
        repository.save(priority);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Priority> findByTitle(String title, Long userId) {
        return repository.findByTitle(Utils.prepareParam(title), userId);
    }

    public void addAll(List<Priority> priorities) {
        repository.saveAll(priorities);
    }
}
