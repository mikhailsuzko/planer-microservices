package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.dto.StatDto;
import com.sma.micro.planner.todo.service.StatService;
import com.sma.micro.planner.todo.service.UserDetailsService;
import com.sma.micro.planner.todo.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;
    private final ValidationService validationService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/stat")
    public ResponseEntity<StatDto> stat() {
        var userId = userDetailsService.getUserId();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(statService.findStat(userId));
    }
}
