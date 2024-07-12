package com.sma.micro.planner.todo.infrastructure.controller.priority;

import com.sma.micro.planner.todo.application.use_case.priority.FindByTitlePrioritiesUseCase;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("priority")
public class FindByTitlePrioritiesController extends CommonController {
    private final FindByTitlePrioritiesUseCase findByTitlePrioritiesUseCase;

    public FindByTitlePrioritiesController(UserDetailsService userDetailsService,
                                           UserValidationService validationService,
                                           FindByTitlePrioritiesUseCase findByTitlePrioritiesUseCase) {
        super(userDetailsService, validationService);
        this.findByTitlePrioritiesUseCase = findByTitlePrioritiesUseCase;
    }

    @PostMapping("/search")
    public ResponseEntity<List<PriorityPublicData>> search(@RequestBody String rq) {
        var userId = getUserId();
        var priorities = findByTitlePrioritiesUseCase.execute(rq, userId);
        return ResponseEntity.ok(priorities);
    }

}
