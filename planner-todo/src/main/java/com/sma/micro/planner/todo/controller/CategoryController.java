package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.plannerutils.rest.webclient.UserWebClientBuilder;
import com.sma.micro.planner.todo.search.SearchValues;
import com.sma.micro.planner.todo.service.CategoryService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final UserWebClientBuilder userWebClientBuilder;

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody Long userId) {
        return categoryService.findAll(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {

        if (category.getId() != null) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userWebClientBuilder.userExist(category.getUserId())) {
            return ResponseEntity.ok(categoryService.add(category));
        }
        return new ResponseEntity("user id=" + category.getUserId() + " not found", HttpStatus.NOT_ACCEPTABLE);

    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Category category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("Missed param id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userWebClientBuilder.userExist(category.getUserId())) {
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity("user id=" + category.getUserId() + " not found", HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody SearchValues params) {
        if (params.userId() != null && userWebClientBuilder.userExist(params.userId())) {
            var categories = categoryService.findByTitle(params.title(), params.userId());
            return ResponseEntity.ok(categories);
        }
        return new ResponseEntity("user id=" + params.userId() + " not found", HttpStatus.NOT_ACCEPTABLE);

    }

    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Element with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
