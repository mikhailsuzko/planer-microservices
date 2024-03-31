package com.sma.micro.planner.plannerusers.controller;

import com.sma.micro.planner.plannerutils.rest.webclient.UserWebClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserWebClientBuilder webClientBuilder;

    @GetMapping("/init")
    public ResponseEntity<Boolean> init(@AuthenticationPrincipal Jwt jwt, @RequestHeader MultiValueMap<String, String> headers) {
        var userId = jwt.getSubject();
        log.info("Initializing user with userId '{}'...", getMask(userId));
        var isInitialized = webClientBuilder.initUser(headers);
        if (isInitialized) {
            log.info("User with id '{}' is initialized", getMask(userId));
            return ResponseEntity.ok(true);
        }
        log.info("User with id '{}' already exists", getMask(userId));
        return ResponseEntity.ok(false);
    }

    private String getMask(String userId) {
        if (isNotBlank(userId) && userId.length() == 36) {
            return userId.substring(0, 6) + "-****-" + userId.substring(32, 36);
        }
        return "";
    }
}
