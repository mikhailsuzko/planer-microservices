package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.sma.micro.planner.todo.application.use_case.priority.FindAllPrioritiesUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("priority")
public class FindAllPrioritiesController extends CommonController {
    private final FindAllPrioritiesUseCase findAllPrioritiesUseCase;

    public FindAllPrioritiesController(UserDetailsService userDetailsService,
                                       UserValidationService validationService,
                                       FindAllPrioritiesUseCase findAllPrioritiesUseCase) {
        super(userDetailsService, validationService);
        this.findAllPrioritiesUseCase = findAllPrioritiesUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PriorityPublicData>> findAll() {
        var userId = getUserId();
        return ResponseEntity.ok(findAllPrioritiesUseCase.execute(userId));
    }
}
