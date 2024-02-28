package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.todo.service.InitDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class InitDataController {
    private final InitDataService initDataService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init(@RequestBody Long userId) {
        return ResponseEntity.ok(initDataService.init(userId));
    }

}
