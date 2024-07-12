package com.sma.micro.planner.todo.application.integration.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.FindByIdCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.sma.micro.planner.todo.common.model.Constants.ID_10;
import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FindByIdCategoryUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindByIdCategoryUseCase service;

    @Test
    void findById() {
        var result = service.execute(100L, USER_ID);

        assertThat(result).isEqualTo(new CategoryPublicData(100L, "Sport", 1L, 1L));
    }

    @Test
    void findById_exception() {
        assertThrows(NoSuchElementException.class, () -> service.execute(ID_10, USER_ID));
    }
}