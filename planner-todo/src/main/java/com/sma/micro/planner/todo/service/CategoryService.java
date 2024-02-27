package com.sma.micro.planner.todo.service;


import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.todo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Category> findAll(Long userId) {
        return repository.findByUserIdOrderByTitle(userId);
    }

    public Category add(Category category) {
        return repository.save(category);
    }

    public void update(Category category) {
        repository.save(category);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String title, Long userId) {
        title = StringUtils.isBlank(title) ? null : "%" + title.toLowerCase() + "%";
        return repository.findByTitle(title, userId);
    }
}
