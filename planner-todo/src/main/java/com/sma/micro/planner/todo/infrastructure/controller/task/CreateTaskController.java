package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.sma.micro.planner.todo.application.use_case.task.CreateTaskUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskRegistrationData;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class CreateTaskController extends CommonController {
    private final CreateTaskUseCase createTaskUseCase;

    public CreateTaskController(UserDetailsService userDetailsService,
                                UserValidationService validationService,
                                CreateTaskUseCase createTaskUseCase) {
        super(userDetailsService, validationService);
        this.createTaskUseCase = createTaskUseCase;
    }

    @PostMapping("/add")
    public ResponseEntity<TaskPublicData> add(@RequestBody @Valid TaskRegistrationData rq) {
        var userId = getUserId();
        return ResponseEntity.ok(createTaskUseCase.execute(rq, userId));
    }
}
