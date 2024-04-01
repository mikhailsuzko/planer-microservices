package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ValidationService {

    public void validateUserIdIsNotEmpty(String userId) {
        if (isBlank(userId)) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("User id='%s' can't be empty", userId));
            throw new ValidationException(statusException);
        }
    }

    public void validateCategoryId(CategoryDto category, boolean isNew) {
        if (isNew && category.id() != null) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Category id='%d' must be null", category.id()));
            throw new ValidationException(statusException);
        } else if (!isNew && (category.id() == null || category.id() == 0)) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Category id='%d' can't be empty", category.id()));
            throw new ValidationException(statusException);
        }

    }
}
