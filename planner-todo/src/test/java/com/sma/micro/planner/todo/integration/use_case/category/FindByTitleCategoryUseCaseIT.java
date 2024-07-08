package com.sma.micro.planner.todo.integration.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.FindByTitleCategoriesUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class FindByTitleCategoryUseCaseIT extends IntegrationTestBase {
    @Autowired
    private FindByTitleCategoriesUseCase service;

    @Test
    void findByTitle() {
        var rq = "om";

        var result = service.execute(rq, USER_ID);

        assertThat(result).hasSize(1)
                .containsExactly(new CategoryPublicData(101L, "Home", 0L, 1L));
    }

}