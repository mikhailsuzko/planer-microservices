package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {FindByIdPriorityUseCase.class, PriorityMapper.class})
class FindByIdPriorityUseCaseTest {
    @Autowired
    private FindByIdPriorityUseCase findPriorityUseCase;
    @MockBean
    private PriorityRepository priorityRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(priorityRepository);
    }

    @Test
    void execute() {
        var priorityExpected = new Priority(ID_10, TITLE, COLOR, USER_ID);
        when(priorityRepository.findById(ID_10, USER_ID)).thenReturn(priorityExpected);

        var result = findPriorityUseCase.execute(ID_10, USER_ID);

        verify(priorityRepository).findById(ID_10, USER_ID);
        assertThat(result).isEqualTo(new PriorityPublicData(ID_10, TITLE, COLOR));
    }
}