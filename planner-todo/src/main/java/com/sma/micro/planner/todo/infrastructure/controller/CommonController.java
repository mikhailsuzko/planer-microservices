package com.sma.micro.planner.todo.infrastructure.controller;

import com.sma.micro.planner.todo.infrastructure.service.UserDetailsService;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
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
