package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.service.InitDataService;
import com.sma.micro.planner.todo.service.UserDetailsService;
import com.sma.micro.planner.todo.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class InitDataController {
    private final InitDataService initDataService;
    private final UserDetailsService userDetailsService;
    private final ValidationService validationService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init() {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(initDataService.init(userId));
    }
}
