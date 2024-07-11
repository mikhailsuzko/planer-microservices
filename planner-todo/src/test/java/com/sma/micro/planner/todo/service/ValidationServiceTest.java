package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.exception.ValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = ValidationService.class)
class ValidationServiceTest {
    @Autowired
    private ValidationService validationService;

    public static Stream<Arguments> emptyUserId() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of((String) null)
        );
    }

    @ParameterizedTest
    @MethodSource("emptyUserId")
    void validateUserIdIsNotEmpty_userIdIsEmpty(String userId) {
        assertThrows(ValidationException.class,
                () -> validationService.validateUserIdIsNotEmpty(userId));
    }

    @ParameterizedTest
    @ValueSource(strings = {"correctId", "1"})
    void validateUserIdIsNotEmpty_userIdIsNotEmpty(String userId) {
        assertDoesNotThrow(() -> validationService.validateUserIdIsNotEmpty(userId));
    }

}