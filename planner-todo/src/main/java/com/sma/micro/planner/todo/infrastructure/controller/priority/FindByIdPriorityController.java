package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.sma.micro.planner.todo.application.use_case.priority.FindByIdPriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("priority")
public class FindByIdPriorityController extends CommonController {
    private final FindByIdPriorityUseCase findByIdPriorityUseCase;

    public FindByIdPriorityController(UserDetailsService userDetailsService,
                                      UserValidationService validationService,
                                      FindByIdPriorityUseCase findByIdPriorityUseCase) {
        super(userDetailsService, validationService);
        this.findByIdPriorityUseCase = findByIdPriorityUseCase;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PriorityPublicData> findById(@PathVariable Long id) {
        var userId = getUserId();
        return ResponseEntity.ok(findByIdPriorityUseCase.execute(id, userId));
    }
}
