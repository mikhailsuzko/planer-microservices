package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.dto.TaskDto;
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
                    "User id can't be empty");
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
                    "Category id can't be empty");
            throw new ValidationException(statusException);
        }

    }

    public void validatePriorityId(PriorityDto priority, boolean isNew) {
        if (isNew && priority.id() != null) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Priority id='%d' must be null", priority.id()));
            throw new ValidationException(statusException);
        } else if (!isNew && (priority.id() == null || priority.id() == 0)) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Priority id can't be empty");
            throw new ValidationException(statusException);
        }
    }

    public void validateTaskId(TaskDto task, boolean isNew) {
        if (isNew && task.id() != null) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Task id='%d' must be null", task.id()));
            throw new ValidationException(statusException);
        } else if (!isNew && (task.id() == null || task.id() == 0)) {
            var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Task id can't be empty");
            throw new ValidationException(statusException);
        }
    }
}
