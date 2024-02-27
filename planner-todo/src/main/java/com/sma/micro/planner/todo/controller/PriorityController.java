package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.todo.search.SearchValues;
import com.sma.micro.planner.todo.service.PriorityService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("priority")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;

    @PostMapping("/all")
    public List<Priority> findAll(@RequestBody Long userId) {
        return priorityService.findAll(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority category) {

        if (category.getId() != null) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Priority category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("Missed param id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", HttpStatus.NOT_ACCEPTABLE);
        }
        priorityService.update(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        priorityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody SearchValues params) {

        var categories = priorityService.findByTitle(params.title(), params.userId());
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id) {
        try {
            return ResponseEntity.ok(priorityService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Element with id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
