package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.repository.CategoryRepository;
import com.sma.micro.planner.todo.service.mapper.CategoryMapper;
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
    private final CategoryMapper mapper;

    public CategoryDto findById(Long id, String userId) {
        var category = repository.findByIdAndUserId(id, userId).orElseThrow();
        return mapper.categoryToDto(category);
    }

    public List<CategoryDto> findAll(String userId) {
        return repository.findByUserIdOrderByTitle(userId)
                .stream()
                .map(mapper::categoryToDto)
                .toList();
    }

    public CategoryDto add(CategoryDto categoryDto, String userId) {

        var category = mapper.dtoToCategory(categoryDto, userId);
        var result = repository.save(category);
        return mapper.categoryToDto(result);
    }

    public void update(CategoryDto categoryDto, String userId) {
        var category = mapper.dtoToCategory(categoryDto, userId);
        repository.save(category);
    }

    public void deleteById(Long id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    public List<CategoryDto> findByTitle(String title, String userId) {
        return repository.findByTitle(Utils.prepareParam(title), userId)
                .stream().map(mapper::categoryToDto)
                .toList();
    }

    public void addAll(List<Category> categories) {
        repository.saveAll(categories);
    }
}
