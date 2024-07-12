package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.sma.micro.planner.todo.application.use_case.priority.UpdatePriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
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
@RequestMapping("priority")
public class UpdatePriorityController extends CommonController {
    private final UpdatePriorityUseCase updatePriorityUseCase;

    public UpdatePriorityController(UserDetailsService userDetailsService,
                                    UserValidationService validationService,
                                    UpdatePriorityUseCase updatePriorityUseCase) {
        super(userDetailsService, validationService);
        this.updatePriorityUseCase = updatePriorityUseCase;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid PriorityUpdateData rq) {
        var userId = getUserId();
        updatePriorityUseCase.execute(rq, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
