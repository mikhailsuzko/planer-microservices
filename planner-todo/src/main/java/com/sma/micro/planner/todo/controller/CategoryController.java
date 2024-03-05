package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Category;
import com.sma.micro.planner.todo.search.SearchValues;
import com.sma.micro.planner.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.sma.micro.planner.plannerutils.util.Utils.userIdNotFound;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
//    private final UserWebClientBuilder userWebClientBuilder;

    @PostMapping("/all")
    public ResponseEntity<List<Category>> findAll(@AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        if (!isBlank(userId)) {
            return ResponseEntity.ok(categoryService.findAll(userId));
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category, @AuthenticationPrincipal Jwt jwt) {
        category.setUserId(jwt.getSubject());
        if (category.getId() != null) {
            return new ResponseEntity("Redundant param: id must be null", NOT_ACCEPTABLE);
        }
        if (isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
//        if (userWebClientBuilder.userExist(category.getUserId())) {
        if (!isBlank(category.getUserId())) {
            return ResponseEntity.ok(categoryService.add(category));
        }
        return new ResponseEntity(userIdNotFound(category.getUserId()), NOT_ACCEPTABLE);

    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Category category, @AuthenticationPrincipal Jwt jwt) {
        category.setUserId(jwt.getSubject());
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("Missed param id", NOT_ACCEPTABLE);
        }
        if (isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
//        if (userWebClientBuilder.userExist(category.getUserId())) {
        if (!isBlank(category.getUserId())) {
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(userIdNotFound(category.getUserId()), NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody SearchValues params, @AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
//        if (params.userId() != null && userWebClientBuilder.userExist(params.userId())) {
        if (!isBlank(userId)) {
            var categories = categoryService.findByTitle(params.title(), userId);
            return ResponseEntity.ok(categories);
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);

    }

    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Element with id=" + id + " not found", NOT_ACCEPTABLE);
        }
    }

}
