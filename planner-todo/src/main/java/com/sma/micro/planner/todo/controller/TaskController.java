package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.dto.TaskDto;
import com.sma.micro.planner.todo.model.search.TaskSearchValues;
import com.sma.micro.planner.todo.service.TaskService;
import com.sma.micro.planner.todo.service.UserDetailsService;
import com.sma.micro.planner.todo.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {
    private static final String TITLE_COLUMN = "title";
    private final TaskService taskService;
    private final ValidationService validationService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/all")
    public ResponseEntity<List<TaskDto>> findAll() {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(taskService.findAll(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<TaskDto> add(@RequestBody @Valid TaskDto task) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        validationService.validateTaskId(task, true);
        return ResponseEntity.ok(taskService.add(task, userId));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid TaskDto task) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        validationService.validateTaskId(task, false);
        taskService.update(task, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        taskService.deleteById(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TaskDto>> search(@RequestBody TaskSearchValues params) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);

        var dateFrom = params.dateFrom() == null ? null : params.dateFrom().atStartOfDay();
        var dateTo = params.dateTo() == null ? null : params.dateTo().atTime(23, 59, 59, 999_999_999);
        var direction = isBlank(params.sortDirection())
                || params.sortDirection().trim().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        var sort = Sort.by(direction, params.sortColumn());
        if (!params.sortColumn().equals(TITLE_COLUMN)) {
            sort = sort.and(Sort.by(Sort.Direction.ASC, TITLE_COLUMN));
        }

        var request = PageRequest.of(params.pageNumber(), params.pageSize(), sort);

        var tasks = taskService.findByParams(params.title(), params.completed(),
                params.priorityId(), params.categoryId(),
                dateFrom, dateTo, userId, request);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(taskService.findById(id, userId));
    }

}
