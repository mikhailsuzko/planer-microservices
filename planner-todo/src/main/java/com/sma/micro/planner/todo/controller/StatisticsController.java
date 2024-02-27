package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Statistics;
import com.sma.micro.planner.plannerutils.rest.UserRestBuilder;
import com.sma.micro.planner.todo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final UserRestBuilder userRestBuilder;

    @PostMapping("/stat")
    public ResponseEntity<Statistics> stat(@RequestBody Long userId) {
        if (userRestBuilder.userExist(userId)) {
            try {
                return ResponseEntity.ok(statisticsService.findStatistics(userId));
            } catch (NoSuchElementException ex) {
                return new ResponseEntity("Statistics for userId=" + userId + " not found", HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity("user id=" + userId + " not found", HttpStatus.NOT_ACCEPTABLE);
    }

}
