package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.sma.micro.planner.todo.application.use_case.priority.CreatePriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
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
@RequestMapping("priority")
public class CreatePriorityController extends CommonController {
    private final CreatePriorityUseCase createPriorityUseCase;

    public CreatePriorityController(UserDetailsService userDetailsService,
                                    UserValidationService validationService,
                                    CreatePriorityUseCase createPriorityUseCase) {
        super(userDetailsService, validationService);
        this.createPriorityUseCase = createPriorityUseCase;
    }

    @PostMapping("/add")
    public ResponseEntity<PriorityPublicData> add(@RequestBody @Valid PriorityRegistrationData rq) {
        var userId = getUserId();
        return ResponseEntity.ok(createPriorityUseCase.execute(rq, userId));
    }
}
