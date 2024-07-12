package com.sma.micro.planner.todo.infrastructure.controller.init;

import com.sma.micro.planner.todo.application.use_case.init.InitDataUseCase;
import com.sma.micro.planner.todo.infrastructure.controller.CommonController;
import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class InitDataController extends CommonController {
    private final InitDataUseCase initDataUseCase;

    public InitDataController(UserDetailsService userDetailsService,
                              UserValidationService validationService,
                              InitDataUseCase initDataUseCase) {
        super(userDetailsService, validationService);
        this.initDataUseCase = initDataUseCase;
    }

    @PostMapping("/init")
    public ResponseEntity<Boolean> init() {
        var userId = getUserId();
        return ResponseEntity.ok(initDataUseCase.execute(userId));
    }
}
