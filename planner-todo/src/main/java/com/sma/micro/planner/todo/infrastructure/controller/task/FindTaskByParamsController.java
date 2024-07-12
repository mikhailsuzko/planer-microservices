package com.sma.micro.planner.todo.infrastructure.controller.task;

import com.sma.micro.planner.todo.application.use_case.task.FindTasksByParamsUseCase;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskPublicData;
import com.sma.micro.planner.todo.application.use_case.task.dto.TaskSearchValues;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class FindTaskByParamsController extends CommonController {
    private final FindTasksByParamsUseCase findTasksByParamsUseCase;

    public FindTaskByParamsController(UserDetailsService userDetailsService,
                                      UserValidationService validationService,
                                      FindTasksByParamsUseCase findTasksByParamsUseCase) {
        super(userDetailsService, validationService);
        this.findTasksByParamsUseCase = findTasksByParamsUseCase;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TaskPublicData>> search(@RequestBody TaskSearchValues params) {
        var userId = getUserId();
        var tasks = findTasksByParamsUseCase.execute(params, userId);
        return ResponseEntity.ok(tasks);
    }

}
