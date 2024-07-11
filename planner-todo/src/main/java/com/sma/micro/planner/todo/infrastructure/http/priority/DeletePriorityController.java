package com.sma.micro.planner.todo.infrastructure.http.priority;

import com.sma.micro.planner.todo.application.use_case.priority.DeletePriorityUseCase;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.http.CommonController;
import com.sma.micro.planner.todo.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("priority")
public class DeletePriorityController extends CommonController {
    private final DeletePriorityUseCase deletePriorityUseCase;

    public DeletePriorityController(UserDetailsService userDetailsService,
                                    UserValidationService validationService,
                                    DeletePriorityUseCase deletePriorityUseCase) {
        super(userDetailsService, validationService);
        this.deletePriorityUseCase = deletePriorityUseCase;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        var userId = getUserId();
        deletePriorityUseCase.execute(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
