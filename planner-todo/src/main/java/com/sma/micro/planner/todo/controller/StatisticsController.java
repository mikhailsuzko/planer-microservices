package com.sma.micro.planner.todo.controller;

import com.sma.micro.planner.plannerentity.entity.Statistics;
import com.sma.micro.planner.plannerutils.rest.rest_template.UserRestBuilder;
import com.sma.micro.planner.todo.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.plannerutils.util.Utils.userIdNotFound;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestController
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final UserRestBuilder userRestBuilder;

    @PostMapping("/stat")
    public ResponseEntity<Statistics> stat(@AuthenticationPrincipal Jwt jwt) {
        var userId = jwt.getSubject();
//        if (userRestBuilder.userExist(userId)) {
        if (!isBlank(userId)) {
            try {
                return ResponseEntity.ok(statisticsService.findStatistics(userId));
            } catch (NoSuchElementException ex) {
                return new ResponseEntity("Statistics for userId=" + userId + " not found", NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity(userIdNotFound(userId), NOT_ACCEPTABLE);
    }

}
