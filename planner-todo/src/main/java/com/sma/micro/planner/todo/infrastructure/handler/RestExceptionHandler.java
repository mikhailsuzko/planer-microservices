package com.sma.micro.planner.todo.infrastructure.handler;

import com.sma.micro.planner.todo.infrastructure.exception.AuthenticationException;
import com.sma.micro.planner.todo.infrastructure.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var message = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                message,
                ((ServletWebRequest) request).getRequest().getRequestURI());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, HttpServletRequest httpRequest) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL().toString());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, HttpServletRequest httpRequest) {
        var statusException = (ResponseStatusException) ex.getCause();
        return buildResponseEntity((HttpStatus) statusException.getStatusCode(),
                statusException.getReason(),
                httpRequest.getRequestURL().toString());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest httpRequest) {
        return buildResponseEntity(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL().toString());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest httpRequest) {
        log.error("Unhandled exception");
        return buildResponseEntity(HttpStatus.SERVICE_UNAVAILABLE,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL().toString());
    }


    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String errorDesc,
                                                       String requestURL) {
        var url = requestURL.replaceAll("[\n\r\t]", "_");
        log.error("Error when calling \"{}\": status={}, errorDesc={}",
                url, status, errorDesc);
        return new ResponseEntity<>(errorDesc, status);
    }
}
