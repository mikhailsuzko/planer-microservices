package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.repository.PriorityRepository;
import com.sma.micro.planner.todo.service.mapper.PriorityMapper;
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
    private final PriorityMapper mapper;

    public PriorityDto findById(Long id, String userId) {
        var priority = repository.findByIdAndUserId(id, userId).orElseThrow();
        return mapper.priorityToDto(priority);
    }

    public List<PriorityDto> findAll(String userId) {
        return repository.findByUserIdOrderByIdAsc(userId).stream()
                .map(mapper::priorityToDto)
                .toList();
    }

    public PriorityDto add(PriorityDto priorityDto, String userId) {
        var priority = mapper.dtoToPriority(priorityDto, userId);
        return mapper.priorityToDto(repository.save(priority));
    }

    public void update(PriorityDto priorityDto, String userId) {
        var priority = mapper.dtoToPriority(priorityDto, userId);
        repository.save(priority);
    }

    public void deleteById(Long id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    public List<PriorityDto> findByTitle(String title, String userId) {
        return repository.findByTitle(Utils.prepareParam(title), userId).stream()
                .map(mapper::priorityToDto)
                .toList();
    }

    public void addAll(List<Priority> priorities) {
        repository.saveAll(priorities);
    }
}
