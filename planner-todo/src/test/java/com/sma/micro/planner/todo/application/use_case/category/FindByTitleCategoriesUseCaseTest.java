package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.plannerutils.util.Utils;
import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FindByTitleCategoriesUseCase.class, CategoryMapper.class})
class FindByTitleCategoriesUseCaseTest {
    @Autowired
    private FindByTitleCategoriesUseCase findByTitleCategoriesUseCase;
    @MockBean
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void execute() {
        var category10Expected = new Category(ID_10, TITLE, COMPLETED_COUNT, UNCOMPLETED_COUNT, USER_ID);
        var category20Expected = new Category(ID_20, TITLE, COMPLETED_COUNT, UNCOMPLETED_COUNT, USER_ID);

        when(categoryRepository.findByTitle(Utils.prepareParam(TITLE), USER_ID)).thenReturn(List.of(category10Expected, category20Expected));

        var result = findByTitleCategoriesUseCase.execute(TITLE, USER_ID);

        assertThat(result).containsExactly(
                new CategoryPublicData(ID_10, TITLE, COMPLETED_COUNT, UNCOMPLETED_COUNT),
                new CategoryPublicData(ID_20, TITLE, COMPLETED_COUNT, UNCOMPLETED_COUNT)
        );
        verify(categoryRepository).findByTitle(Utils.prepareParam(TITLE), USER_ID);
    }
}