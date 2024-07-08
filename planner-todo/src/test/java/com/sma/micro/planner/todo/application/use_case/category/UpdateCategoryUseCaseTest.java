package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryUpdateData;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {UpdateCategoryUseCase.class, CategoryMapper.class})
class UpdateCategoryUseCaseTest {
    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;
    @MockBean
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void execute() {
        var rq = new CategoryUpdateData(ID_10, TITLE);
        var category = new Category(ID_10, TITLE, 0L, 0L, USER_ID);

        updateCategoryUseCase.execute(rq, USER_ID);

        verify(categoryRepository).update(category);
    }
}