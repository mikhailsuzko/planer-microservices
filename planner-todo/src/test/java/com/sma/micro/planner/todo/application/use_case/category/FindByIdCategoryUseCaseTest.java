package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.application.use_case.category.dto.CategoryPublicData;
import com.sma.micro.planner.todo.domain.entity.Category;
import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FindByIdCategoryUseCase.class, CategoryMapper.class})
class FindByIdCategoryUseCaseTest {
    @Autowired
    private FindByIdCategoryUseCase findCategoryUseCase;
    @MockBean
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void execute() {
        var categoryExpected = new Category(ID_10, TITLE, COMPLETED_COUNT, UNCOMPLETED_COUNT, USER_ID);
        when(categoryRepository.findById(ID_10, USER_ID)).thenReturn(categoryExpected);

        var result = findCategoryUseCase.execute(ID_10, USER_ID);

        verify(categoryRepository).findById(ID_10, USER_ID);
        assertThat(result).isEqualTo(new CategoryPublicData(ID_10, TITLE, COMPLETED_COUNT, UNCOMPLETED_COUNT));
    }
}