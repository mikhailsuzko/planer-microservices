package com.sma.micro.planner.todo.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final Throwable cause;

    public ValidationException(Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }
}
