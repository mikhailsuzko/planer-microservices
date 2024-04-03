package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.model.search.SearchValues;
import com.sma.micro.planner.todo.service.PriorityService;
import com.sma.micro.planner.todo.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("priority")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;
    private final ValidationService validationService;

    @GetMapping("/all")
    public ResponseEntity<List<PriorityDto>> findAll(@AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(priorityService.findAll(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<PriorityDto> add(@RequestBody @Valid PriorityDto priority,
                                           @AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        validationService.validateUserIdIsNotEmpty(userId);
        validationService.validatePriorityId(priority, true);
        return ResponseEntity.ok(priorityService.add(priority, userId));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid PriorityDto priority,
                                       @AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        validationService.validateUserIdIsNotEmpty(userId);
        validationService.validatePriorityId(priority, false);
        priorityService.update(priority, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        priorityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PriorityDto>> search(@RequestBody SearchValues params,
                                                    @AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        validationService.validateUserIdIsNotEmpty(userId);
        var categories = priorityService.findByTitle(params.title(), userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PriorityDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(priorityService.findById(id));
    }
}
