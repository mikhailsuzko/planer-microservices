package com.sma.micro.planner.todo.infrastructure.http;

import com.sma.micro.planner.todo.application.use_case.validation.UserValidationService;
import com.sma.micro.planner.todo.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
public abstract class CommonController {
    private final UserDetailsService userDetailsService;
    private final UserValidationService validationService;

    protected String getUserId() {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return userId;
    }
}
