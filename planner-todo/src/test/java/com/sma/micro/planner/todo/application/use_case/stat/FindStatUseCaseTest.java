package com.sma.micro.planner.todo.application.use_case.stat;

import com.sma.micro.planner.todo.application.use_case.stat.dto.StatPublicData;
import com.sma.micro.planner.todo.domain.entity.Stat;
import com.sma.micro.planner.todo.domain.repository.StatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = FindStatUseCase.class)
class FindStatUseCaseTest {
    @Autowired
    private FindStatUseCase findPriorityUseCase;
    @MockBean
    private StatRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void execute() {
        var priorityExpected = new Stat(ID_10, COUNT, COUNT, USER_ID);
        when(repository.find(USER_ID)).thenReturn(priorityExpected);

        var result = findPriorityUseCase.execute(USER_ID);

        verify(repository).find(USER_ID);
        assertThat(result).isEqualTo(new StatPublicData(COUNT, COUNT));
    }
}