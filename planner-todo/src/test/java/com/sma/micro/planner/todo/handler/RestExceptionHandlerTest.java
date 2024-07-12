package com.sma.micro.planner.todo.handler;

import com.sma.micro.planner.todo.infrastructure.exception.AuthenticationException;
import com.sma.micro.planner.todo.infrastructure.exception.ValidationException;
import com.sma.micro.planner.todo.infrastructure.handler.RestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RestExceptionHandler.class)
class RestExceptionHandlerTest {

    public static final MockHttpServletRequest HTTP_REQUEST = new MockHttpServletRequest();
    @Autowired
    private RestExceptionHandler handler;

    @Test
    void handleIllegalArgumentException() {
        var message = "Invalid argument";
        var exception = new AuthenticationException(message);
        var result = handler.handleAuthenticationException(exception, HTTP_REQUEST);

        assertThat(result).isEqualTo(new ResponseEntity<>(message, HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleValidationException() {
        var reason = "Category id='10' must be null";
        var statusException = new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
        var exception = new ValidationException(statusException);

        var result = handler.handleValidationException(exception, HTTP_REQUEST);

        assertThat(result).isEqualTo(new ResponseEntity<>(reason, HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleNoSuchElementException() {
        var message = "No value present";
        var exception = new NoSuchElementException(message);
        var result = handler.handleNoSuchElementException(exception, HTTP_REQUEST);

        assertThat(result).isEqualTo(new ResponseEntity<>(message, HttpStatus.NOT_FOUND));
    }

    @Test
    void handleException() {
        var message = "Unknown exception";
        var exception = new RuntimeException(message);
        var result = handler.handleException(exception, HTTP_REQUEST);

        assertThat(result).isEqualTo(new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE));

    }
}