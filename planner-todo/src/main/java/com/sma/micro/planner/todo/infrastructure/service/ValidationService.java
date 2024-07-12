package com.sma.micro.planner.todo.infrastructure.service;

import com.sma.micro.planner.todo.infrastructure.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ValidationService {

    public void validateUserIdIsNotEmpty(String userId) {
        if (isBlank(userId)) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User id can't be empty");
            throw new ValidationException(statusException);
        }
    }
}
