package com.sma.micro.planner.todo.application.use_case.stat;

import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.domain.repository.StatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.model.Constants.USER_ID;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = CreateStatUseCase.class)
class CreateStatUseCaseTest {
    @Autowired
    private CreateStatUseCase createStatUseCase;
    @MockBean
    private StatRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void execute() {
        var stat = Stat.builder().userId(USER_ID).build();

        createStatUseCase.execute(USER_ID);

        verify(repository).add(stat);
    }
}