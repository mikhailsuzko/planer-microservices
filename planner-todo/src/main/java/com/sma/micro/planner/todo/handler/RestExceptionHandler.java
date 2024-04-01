package com.sma.micro.planner.todo.handler;

import com.sma.micro.planner.todo.dto.ErrorData;
import com.sma.micro.planner.todo.exception.ValidationException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
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
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest httpRequest) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL().toString());
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, HttpServletRequest httpRequest) {
        var statusException = (ResponseStatusException) ex.getCause();
        return buildResponseEntity((HttpStatus) statusException.getStatusCode(),
                statusException.getReason(),
                httpRequest.getRequestURL().toString());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest httpRequest) {
        return buildResponseEntity(HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL().toString());
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, HttpServletRequest httpRequest) {
        log.error("Unhandled exception");
        return buildResponseEntity(HttpStatus.SERVICE_UNAVAILABLE,
                ex.getLocalizedMessage(),
                httpRequest.getRequestURL().toString());
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        var httpStatus = HttpStatus.resolve(status.value());
        httpStatus = httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;
        return buildResponseEntity(httpStatus,
                ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(". ")),
                ((ServletWebRequest) request).getRequest().getRequestURL().toString());
    }


    @NonNull
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, Object body,
                                                             @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatusCode statusCode,
                                                             @NonNull WebRequest request) {
        var httpStatus = HttpStatus.resolve(statusCode.value());
        httpStatus = httpStatus == null ? HttpStatus.INTERNAL_SERVER_ERROR : httpStatus;
        return buildResponseEntity(httpStatus,
                ex.getLocalizedMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURL().toString());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String errorDesc,
                                                       String requestURL) {
        var errorData = new ErrorData(String.valueOf(status.value()), status.name(), errorDesc);
        var url = requestURL.replaceAll("[\n\r\t]", "_");
        log.error("Error when calling \"{}\": {}", url, errorData);
        return new ResponseEntity<>(errorDesc, status);
    }
}
