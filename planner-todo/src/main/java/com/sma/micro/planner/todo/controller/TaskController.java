package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Task;
import com.sma.micro.planner.plannerutils.rest.rest_template.UserRestBuilder;
import com.sma.micro.planner.todo.search.TaskSearchValues;
import com.sma.micro.planner.todo.service.TaskService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.sma.micro.planner.plannerutils.util.Utils.userIdNotFound;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TaskController {
    private static final String ID_COLUMN = "id";
    private final TaskService taskService;
    private final UserRestBuilder userRestBuilder;

    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody Long userId) {
        if (userRestBuilder.userExist(userId)) {
            return ResponseEntity.ok(taskService.findAll(userId));
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);

    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {

        if (task.getId() != null) {
            return new ResponseEntity("Redundant param: id must be null", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(task.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
        if (userRestBuilder.userExist(task.getUserId())) {
            return ResponseEntity.ok(taskService.add(task));
        }
        return new ResponseEntity(userIdNotFound(task.getUserId()), NOT_ACCEPTABLE);

    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("Missed param id", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(task.getTitle())) {
            return new ResponseEntity("Missed param title", NOT_ACCEPTABLE);
        }
        if (userRestBuilder.userExist(task.getUserId())) {
            taskService.update(task);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity(userIdNotFound(task.getUserId()), NOT_ACCEPTABLE);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues params) {

        if (params.userId() == null) {
            return new ResponseEntity("Missed param userId", NOT_ACCEPTABLE);
        }
        if (userRestBuilder.userExist(params.userId())) {

            var dateFrom = params.dateFrom() == null ? null : params.dateFrom().atStartOfDay();
            var dateTo = params.dateTo() == null ? null : params.dateTo().atTime(23, 59, 59, 999_999_999);
            var direction = StringUtils.isBlank(params.sortDirection())
                    || params.sortDirection().trim().equalsIgnoreCase("ask")
                    ? Sort.Direction.ASC : Sort.Direction.DESC;
            var sort = Sort.by(direction, params.sortColumn(), ID_COLUMN);
            PageRequest request = PageRequest.of(params.pageNumber(), params.pageSize(), sort);

            var tasks = taskService.findByParams(params.title(), params.completed(),
                    params.priorityId(), params.categoryId(),
                    dateFrom, dateTo,
                    params.userId(), request);
            return ResponseEntity.ok(tasks);
        }
        return new ResponseEntity(userIdNotFound(params.userId()), NOT_ACCEPTABLE);
    }

    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id) {
        try {
            return ResponseEntity.ok(taskService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("Element with id=" + id + " not found", NOT_ACCEPTABLE);
        }
    }

}
