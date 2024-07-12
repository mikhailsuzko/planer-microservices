package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.sma.micro.planner.todo.application.use_case.task.DeleteTaskUseCase;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class DeleteTaskController extends CommonController {
    private final DeleteTaskUseCase deleteTaskUseCase;

    public DeleteTaskController(UserDetailsService userDetailsService,
                                UserValidationService validationService,
                                DeleteTaskUseCase deleteTaskUseCase) {
        super(userDetailsService, validationService);
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        var userId = getUserId();
        deleteTaskUseCase.execute(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
