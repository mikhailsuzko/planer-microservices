package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.dto.TaskDto;
import com.sma.micro.planner.todo.repository.TaskRepository;
import com.sma.micro.planner.todo.service.mapper.TaskMapper;
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
    private final TaskMapper mapper;

    public TaskDto findById(Long id) {
        var task = repository.findById(id).orElseThrow();
        return mapper.taskToDto(task);
    }

    public List<TaskDto> findAll(String userId) {
        return repository.findByUserIdOrderByTaskDateDescTitleAsc(userId).stream()
                .map(mapper::taskToDto)
                .toList();
    }

    public TaskDto add(TaskDto taskDto, String userId) {
        var task = mapper.dtoToTask(taskDto, userId);
        return mapper.taskToDto(repository.save(task));
    }

    public void update(TaskDto taskDto, String userId) {
        var task = mapper.dtoToTask(taskDto, userId);
        repository.save(task);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<TaskDto> findByParams(String title,
                                   Boolean completed,
                                   Long priorityId,
                                   Long categoryId,
                                   LocalDateTime dateFrom,
                                   LocalDateTime dateTo,
                                   String userId, Pageable pageable) {
        return repository.findByParams(Utils.prepareParam(title), completed, priorityId, categoryId,
                dateFrom, dateTo, userId, pageable).map(mapper::taskToDto);
    }

    public void addAll(List<Task> tasks) {
        repository.saveAll(tasks);
    }
}
