package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Priority;
import com.sma.micro.planner.todo.search.SearchValues;
import com.sma.micro.planner.todo.service.PriorityService;
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
@RequestMapping("priority")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;
//    private final UserFeignClient userFeignClient;

    @GetMapping("/all")
    public ResponseEntity<List<Priority>> findAll(@AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        if (!isBlank(userId)) {
//        if (userExist(userId)) {
            return ResponseEntity.ok(priorityService.findAll(userId));
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);
    }


    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority, @AuthenticationPrincipal Jwt jwt) {
        priority.setUserId(jwt.getSubject());
        if (priority.getId() != null) {
            return new ResponseEntity("Redundant param: id must be null", NOT_ACCEPTABLE);
        }
        if (isBlank(priority.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priorityService.add(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Priority priority, @AuthenticationPrincipal Jwt jwt) {
        priority.setUserId(jwt.getSubject());
        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("Missed param id", NOT_ACCEPTABLE);
        }
        if (isBlank(priority.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
        priorityService.update(priority);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        priorityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody SearchValues params, @AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        if (!isBlank(userId)) {
            var categories = priorityService.findByTitle(params.title(), userId);
            return ResponseEntity.ok(categories);
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(priorityService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Element with id=" + id + " not found", NOT_ACCEPTABLE);
        }
    }

}
