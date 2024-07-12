package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.sma.micro.planner.todo.application.use_case.task.FindAllTasksUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("task")
public class FindAllTasksController extends CommonController {
    private final FindAllTasksUseCase findAllTasksUseCase;

    public FindAllTasksController(UserDetailsService userDetailsService,
                                  UserValidationService validationService,
                                  FindAllTasksUseCase findAllTasksUseCase) {
        super(userDetailsService, validationService);
        this.findAllTasksUseCase = findAllTasksUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskPublicData>> findAll() {
        var userId = getUserId();
        return ResponseEntity.ok(findAllTasksUseCase.execute(userId));
    }
}
