package com.sma.micro.planner.todo.integration.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.FindAllCategoriesUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;


class FindAllCategoryUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindAllCategoriesUseCase service;

    @Test
    void findAll() {
        var result = service.execute(USER_ID);

        assertThat(result).hasSize(4)
                .containsExactlyInAnyOrder(
                        new CategoryPublicData(100L, "Sport", 1L, 1L),
                        new CategoryPublicData(101L, "Home", 0L, 1L),
                        new CategoryPublicData(102L, "Travelling", 0L, 1L),
                        new CategoryPublicData(103L, "Hobby", 1L, 0L)
                );
    }


}