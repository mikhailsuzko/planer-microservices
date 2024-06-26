package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.model.search.SearchValues;
import com.sma.micro.planner.todo.service.CategoryService;
import com.sma.micro.planner.todo.service.UserDetailsService;
import com.sma.micro.planner.todo.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ValidationService validationService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> findAll() {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(categoryService.findAll(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> add(@RequestBody @Valid CategoryDto category) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        validationService.validateCategoryId(category, true);
        return ResponseEntity.ok(categoryService.add(category, userId));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid CategoryDto category) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        validationService.validateCategoryId(category, false);
        categoryService.update(category, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        categoryService.deleteById(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<CategoryDto>> search(@RequestBody SearchValues params) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        var categories = categoryService.findByTitle(params.title(), userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(categoryService.findById(id, userId));
    }

}
