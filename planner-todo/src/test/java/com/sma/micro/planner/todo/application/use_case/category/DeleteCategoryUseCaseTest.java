package com.sma.micro.planner.todo.application.use_case.category;

import com.sma.micro.planner.todo.domain.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.model.Constants.ID_10;
import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = DeleteCategoryUseCase.class)
class DeleteCategoryUseCaseTest {
    @Autowired
    private DeleteCategoryUseCase deleteCategoryUseCase;
    @MockBean
    private CategoryRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void execute() {

        deleteCategoryUseCase.execute(ID_10, USER_ID);

        verify(repository).delete(ID_10, USER_ID);
    }
}