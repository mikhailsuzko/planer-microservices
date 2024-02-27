package com.sma.micro.planner.plannerusers.controller;

import com.sma.micro.planner.plannerentity.entity.User;
import com.sma.micro.planner.plannerusers.search.UserSearchValue;
import com.sma.micro.planner.plannerusers.service.UserService;
import com.sma.micro.planner.plannerutils.rest.webclient.UserWebClientBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    public static final String ID_COLUMN = "id";
    private final UserService userService;
    private final UserWebClientBuilder userWebClientBuilder;

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        if (user.getId() != null && user.getId() != 0) {
            return new ResponseEntity("Redundant param: id must be null", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(user.getEmail())) {
            return new ResponseEntity("Missed param: email ", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return new ResponseEntity("Missed param: password ", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(user.getUsername())) {
            return new ResponseEntity("Missed param: username ", NOT_ACCEPTABLE);
        }
        user = userService.add(user);
        if (user != null) {
            userWebClientBuilder.initUserData(user.getId()).subscribe(result -> {
                if (result) {
                    log.info("user populated");
                } else {
                    log.info("user not populated");
                }
            });
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        if (user.getId() == null || user.getId() == 0) {
            return new ResponseEntity("Missed param: id", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(user.getEmail())) {
            return new ResponseEntity("Missed param: email ", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return new ResponseEntity("Missed param: password ", NOT_ACCEPTABLE);
        }
        if (StringUtils.isBlank(user.getUsername())) {
            return new ResponseEntity("Missed param: username ", NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(userService.update(user));
    }

    @PostMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        userService.delete(id);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/deleteByEmail")
    public ResponseEntity<Void> deleteByEmail(@RequestBody String email) {
        userService.delete(email);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/id")
    public ResponseEntity<User> findById(@RequestBody Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("id " + id + " not found", NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/email")
    public ResponseEntity<User> findByEmail(@RequestBody String email) {
        try {
            return ResponseEntity.ok(userService.findByEmail(email));
        } catch (NoSuchElementException ex) {
            return new ResponseEntity("email " + email + " not found", NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<User>> search(@RequestBody UserSearchValue params) {
        var direction = StringUtils.isBlank(params.sortDirection())
                || params.sortDirection().trim().equalsIgnoreCase("ask")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        var sort = Sort.by(direction, params.sortColumn(), ID_COLUMN);
        var request = PageRequest.of(params.pageNumber(), params.pageSize(), sort);

        return ResponseEntity.ok(userService.findByParams(params.username(), params.email(), request));
    }
}
