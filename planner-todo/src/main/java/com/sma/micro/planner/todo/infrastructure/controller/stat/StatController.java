package com.sma.micro.planner.todo.infrastructure.controller.stat;

import com.sma.micro.planner.todo.application.use_case.stat.FindStatUseCase;
import com.sma.micro.planner.todo.application.use_case.stat.dto.StatPublicData;
import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController extends CommonController {
    private final FindStatUseCase findStatUseCase;

    public StatController(UserDetailsService userDetailsService,
                          UserValidationService validationService,
                          FindStatUseCase findStatUseCase) {
        super(userDetailsService, validationService);
        this.findStatUseCase = findStatUseCase;
    }

    @GetMapping("/stat")
    public ResponseEntity<StatPublicData> stat() {
        var userId = getUserId();
        return ResponseEntity.ok(findStatUseCase.execute(userId));
    }
}
