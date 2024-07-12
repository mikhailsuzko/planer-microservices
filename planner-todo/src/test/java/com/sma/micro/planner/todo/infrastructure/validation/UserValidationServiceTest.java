package com.sma.micro.planner.todo.infrastructure.validation;

import com.sma.micro.planner.todo.infrastructure.exception.ValidationException;
import com.sma.micro.planner.todo.infrastructure.service.UserValidationService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = UserValidationService.class)
class UserValidationServiceTest {
    @Autowired
    private UserValidationService validationService;

    @ParameterizedTest
    @ValueSource(strings = {"id", "1"})
    void validateUserIdIsNotEmpty_userIdIsNotEmpty(String userId) {
        assertDoesNotThrow(() -> validationService.validateUserIdIsNotEmpty(userId));
    }

    private static Stream<Arguments> emptyUserId() {
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
}