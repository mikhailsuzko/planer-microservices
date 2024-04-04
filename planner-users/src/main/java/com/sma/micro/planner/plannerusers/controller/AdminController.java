package com.sma.micro.planner.plannerusers.controller;

import com.sma.micro.planner.plannerusers.dto.UserDto;
import com.sma.micro.planner.plannerusers.service.keycloak.KeycloakUtils;
import com.sma.micro.planner.plannerutils.model.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class AdminController {
    private final KeycloakUtils keycloakUtils;
    private static final List<String> defaultUserRoles = List.of(Roles.USER.getName());

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody UserDto user) {
        if (isBlank(user.getEmail())) {
            return new ResponseEntity("Missed param: email ", NOT_ACCEPTABLE);
        }
        if (isBlank(user.getPassword())) {
            return new ResponseEntity("Missed param: password ", NOT_ACCEPTABLE);
        }
        if (isBlank(user.getUsername())) {
            return new ResponseEntity("Missed param: username ", NOT_ACCEPTABLE);
        }
        var response = keycloakUtils.createKeycloakUser(user);
        if (response.getStatus() == CONFLICT.value()) {
            return new ResponseEntity("User or email already exists", CONFLICT);
        }
        var userId = CreatedResponseUtil.getCreatedId(response);
        log.info("User created with id '{}'", userId);
        keycloakUtils.addRoles(userId, defaultUserRoles);
        var message = response.getStatus() == CREATED.value()
                ? "User crated" : response.getStatusInfo().getReasonPhrase();
        return ResponseEntity.status(response.getStatus()).body(message);
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserDto user) {
        if (isBlank(user.getId())) {
            return new ResponseEntity("Missed param: id", NOT_ACCEPTABLE);
        }
        if (isBlank(user.getEmail())) {
            return new ResponseEntity("Missed param: email ", NOT_ACCEPTABLE);
        }
        if (isBlank(user.getPassword())) {
            return new ResponseEntity("Missed param: password ", NOT_ACCEPTABLE);
        }
        if (isBlank(user.getUsername())) {
            return new ResponseEntity("Missed param: username ", NOT_ACCEPTABLE);
        }
        keycloakUtils.update(user);
        return new ResponseEntity("User updated", OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<String> deleteById(@RequestBody String id) {
        var response = keycloakUtils.deleteUser(id);
        return ResponseEntity.status(response.getStatus()).build();
    }

    @PostMapping("/id")
    public ResponseEntity<UserRepresentation> findById(@RequestBody String id) {
        try {
            return ResponseEntity.ok(keycloakUtils.findUserById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("user id " + id + " not found", NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserRepresentation>> search(@RequestBody String email) {
        return ResponseEntity.ok(keycloakUtils.findUser(email));
    }
}
