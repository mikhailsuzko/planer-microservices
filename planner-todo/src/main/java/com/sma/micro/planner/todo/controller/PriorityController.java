package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.todo.feign.UserFeignClient;
import com.sma.micro.planner.todo.search.SearchValues;
import com.sma.micro.planner.todo.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.sma.micro.planner.plannerutils.util.Utils.userIdNotFound;
import static io.micrometer.common.util.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestController
@RequestMapping("priority")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/all")
    public ResponseEntity<List<Priority>> findAll(@RequestBody Long userId) {
        if (userExist(userId)) {
            return ResponseEntity.ok(priorityService.findAll(userId));
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority category) {

        if (category.getId() != null) {
            return new ResponseEntity("Redundant param: id must be null", NOT_ACCEPTABLE);
        }
        if (isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
        if (userExist(category.getUserId())) {
            return ResponseEntity.ok(priorityService.add(category));
        }
        return new ResponseEntity(userIdNotFound(category.getUserId()), NOT_ACCEPTABLE);
    }


    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Priority category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("Missed param id", NOT_ACCEPTABLE);
        }
        if (isBlank(category.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
        if (userExist(category.getUserId())) {
            priorityService.update(category);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(userIdNotFound(category.getUserId()), NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        priorityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody SearchValues params) {
        if (userExist(params.userId())) {
            var categories = priorityService.findByTitle(params.title(), params.userId());
            return ResponseEntity.ok(categories);
        }
        return new ResponseEntity(userIdNotFound(params.userId()), NOT_ACCEPTABLE);
    }

    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id) {
        try {
            return ResponseEntity.ok(priorityService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Element with id=" + id + " not found", NOT_ACCEPTABLE);
        }
    }

    private boolean userExist(Long userId) {
        return userFeignClient.findUserById(userId) != null;
    }
}
