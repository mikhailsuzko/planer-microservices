package com.sma.micro.planner.todo.db;

import com.sma.micro.planner.todo.db.jpa.CategoryJpaRepository;
import com.sma.micro.planner.todo.db.mapper.CategoryMapper;
import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SqlCategoryRepository implements CategoryRepository {
    private final CategoryJpaRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> findByUserIdOrderByTitle(String userId) {
        return repository.findByUserIdOrderByTitle(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<CategoryDto> findByTitle(String title, String userId) {
        return repository.findByTitle(title, userId)
                .stream().map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteByIdAndUserId(Long id, String userId) {
        repository.deleteByIdAndUserId(id, userId);
    }

    @Override
    public CategoryDto findByIdAndUserId(Long id, String userId) {
        var category = repository.findByIdAndUserId(id, userId).orElseThrow();
        return mapper.toDomain(category);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto, String userId) {
        var category = mapper.toEntity(categoryDto, userId);
        var result = repository.save(category);
        return mapper.toDomain(result);
    }

    @Override
    public void saveAll(List<CategoryDto> categories, String userId) {
        var categoriesDto = categories.stream()
                .map(c -> mapper.toEntity(c, userId))
                .toList();
        repository.saveAll(categoriesDto);
    }
}
