package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.sma.micro.planner.todo.application.use_case.task.UpdateTaskUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskUpdateData;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class UpdateTaskController extends CommonController {
    private final UpdateTaskUseCase updateTaskUseCase;

    public UpdateTaskController(UserDetailsService userDetailsService,
                                UserValidationService validationService,
                                UpdateTaskUseCase updateTaskUseCase) {
        super(userDetailsService, validationService);
        this.updateTaskUseCase = updateTaskUseCase;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid TaskUpdateData rq) {
        var userId = getUserId();
        updateTaskUseCase.execute(rq, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
