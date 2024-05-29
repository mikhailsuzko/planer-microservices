package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryDto findById(Long id, String userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    public List<CategoryDto> findAll(String userId) {
        return repository.findByUserIdOrderByTitle(userId);
    }

    public CategoryDto add(CategoryDto categoryDto, String userId) {
        return repository.save(categoryDto, userId);
    }

    public void update(CategoryDto categoryDto, String userId) {
        repository.save(categoryDto, userId);
    }

    public void deleteById(Long id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    public List<CategoryDto> findByTitle(String title, String userId) {
        return repository.findByTitle(Utils.prepareParam(title), userId);
    }

    public void addAll(List<CategoryDto> categories, String userId) {
        repository.saveAll(categories, userId);
    }
}
