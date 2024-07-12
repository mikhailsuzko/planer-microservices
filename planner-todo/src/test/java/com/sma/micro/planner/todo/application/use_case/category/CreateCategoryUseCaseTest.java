package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryRegistrationData;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CreateCategoryUseCase.class, CategoryMapper.class})
class CreateCategoryUseCaseTest {
    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;
    @MockBean
    private CategoryRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void execute() {
        var rq = new CategoryRegistrationData(TITLE);
        var categoryExpected = new Category(ID_10, TITLE, 0L, 0L, USER_ID);
        var newCategory = getNewCategory();
        when(repository.add(newCategory)).thenReturn(categoryExpected);

        var result = createCategoryUseCase.execute(rq, USER_ID);

        assertThat(result).isEqualTo(new CategoryPublicData(ID_10, TITLE, 0L, 0L));
        verify(repository).add(newCategory);
    }

    private static @NotNull Category getNewCategory() {
        var category = Category.builder();
        category.title(TITLE);
        category.userId(USER_ID);
        return category.build();
    }
}