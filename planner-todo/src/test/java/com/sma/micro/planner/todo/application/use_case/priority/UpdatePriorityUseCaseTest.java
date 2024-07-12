package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityUpdateData;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.common.model.Constants.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest(classes = {UpdatePriorityUseCase.class, PriorityMapper.class})
class UpdatePriorityUseCaseTest {
    @Autowired
    private UpdatePriorityUseCase updatePriorityUseCase;
    @MockBean
    private PriorityRepository priorityRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(priorityRepository);
    }

    @Test
    void execute() {
        var rq = new PriorityUpdateData(ID_10, TITLE, COLOR);
        var priority = new Priority(ID_10, TITLE, COLOR, USER_ID);

        updatePriorityUseCase.execute(rq, USER_ID);

        verify(priorityRepository).update(priority);
    }
}