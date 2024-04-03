package com.sma.micro.planner.todo.service;

import com.sma.micro.planner.todo.dto.CategoryDto;
import com.sma.micro.planner.todo.dto.PriorityDto;
import com.sma.micro.planner.todo.dto.TaskDto;
import com.sma.micro.planner.todo.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static com.sma.micro.planner.todo.model.Constants.COLOR;
import static com.sma.micro.planner.todo.model.Constants.TITLE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = ValidationService.class)
class ValidationServiceTest {
    public static final CategoryDto NEW_CATEGORY = new CategoryDto(null, TITLE, 0L, 0L);
    public static final CategoryDto OLD_CATEGORY = new CategoryDto(23L, TITLE, 0L, 0L);
    public static final PriorityDto NEW_PRIORITY = new PriorityDto(null, TITLE, COLOR);
    public static final PriorityDto OLD_PRIORITY = new PriorityDto(100L, TITLE, COLOR);
    public static final TaskDto NEW_TASK = new TaskDto(null, TITLE, false, null, null, null);
    public static final TaskDto OLD_TASK = new TaskDto(15L, TITLE, true, null, null, null);
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

    @Test
    void validateCategoryId_newCategory() {
        assertDoesNotThrow(() -> validationService.validateCategoryId(NEW_CATEGORY, true));
        assertDoesNotThrow(() -> validationService.validateCategoryId(OLD_CATEGORY, false));
        assertThrows(ValidationException.class,
                () -> validationService.validateCategoryId(OLD_CATEGORY, true));
        assertThrows(ValidationException.class,
                () -> validationService.validateCategoryId(NEW_CATEGORY, false));
    }

    @Test
    void validatePriorityId() {
        assertDoesNotThrow(() -> validationService.validatePriorityId(NEW_PRIORITY, true));
        assertDoesNotThrow(() -> validationService.validatePriorityId(OLD_PRIORITY, false));
        assertThrows(ValidationException.class,
                () -> validationService.validatePriorityId(OLD_PRIORITY, true));
        assertThrows(ValidationException.class,
                () -> validationService.validatePriorityId(NEW_PRIORITY, false));
    }

    @Test
    void validateTaskId() {
        assertDoesNotThrow(() -> validationService.validateTaskId(NEW_TASK, true));
        assertDoesNotThrow(() -> validationService.validateTaskId(OLD_TASK, false));
        assertThrows(ValidationException.class,
                () -> validationService.validateTaskId(OLD_TASK, true));
        assertThrows(ValidationException.class,
                () -> validationService.validateTaskId(NEW_TASK, false));
    }
}