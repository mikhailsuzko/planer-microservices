package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.service.InitDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class InitDataController {
    private final InitDataService initDataService;

    @GetMapping("/init")
    public ResponseEntity<Boolean> init(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(initDataService.init(jwt.getSubject()));
    }

}
