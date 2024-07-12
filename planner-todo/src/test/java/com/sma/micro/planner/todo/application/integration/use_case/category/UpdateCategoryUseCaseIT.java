package com.sma.micro.planner.todo.application.integration.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.UpdateCategoryUseCase;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.common.IntegrationTestBase;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sma.micro.planner.todo.common.model.Constants.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;


class UpdateCategoryUseCaseIT extends IntegrationTestBase {
    @Autowired
    private UpdateCategoryUseCase service;
    @Autowired
    private CategoryRepository repository;

    @Test
    void update() {
        var id = 102L;
        var category = new CategoryUpdateData(id, "Travel");
        var result = repository.findById(id, USER_ID);
        assertThat(result.getTitle()).isNotEqualTo(category.title());

        service.execute(category, USER_ID);

        result = repository.findById(id, USER_ID);
        assertThat(result.getTitle()).isEqualTo(category.title());
    }
}