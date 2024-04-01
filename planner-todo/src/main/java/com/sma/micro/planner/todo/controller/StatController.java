package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.dto.StatDto;
import com.sma.micro.planner.todo.service.StatService;
import com.sma.micro.planner.todo.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;
    private final ValidationService validationService;

    @GetMapping("/stat")
    public ResponseEntity<StatDto> stat(@AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
        validationService.validateUserIdIsNotEmpty(userId);
        return ResponseEntity.ok(statService.findStat(userId));
    }
}
