package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.sma.micro.planner.todo.application.use_case.task.FindTaskByIdUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class FindTaskByIdController extends CommonController {
    private final FindTaskByIdUseCase findTaskByIdUseCase;

    public FindTaskByIdController(UserDetailsService userDetailsService,
                                  UserValidationService validationService,
                                  FindTaskByIdUseCase findTaskByIdUseCase) {
        super(userDetailsService, validationService);
        this.findTaskByIdUseCase = findTaskByIdUseCase;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskPublicData> findById(@PathVariable Long id) {
        var userId = getUserId();
        return ResponseEntity.ok(findTaskByIdUseCase.execute(id, userId));
    }
}
