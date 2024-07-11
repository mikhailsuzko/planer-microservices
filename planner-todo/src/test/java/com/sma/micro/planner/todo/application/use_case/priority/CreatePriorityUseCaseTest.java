package com.sma.micro.planner.todo.application.use_case.priority;

import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityPublicData;
import com.sma.micro.planner.todo.application.use_case.priority.dto.PriorityRegistrationData;
import com.sma.micro.planner.todo.domain.entity.Priority;
import com.sma.micro.planner.todo.domain.repository.PriorityRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.sma.micro.planner.todo.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CreatePriorityUseCase.class, PriorityMapper.class})
class CreatePriorityUseCaseTest {
    @Autowired
    private CreatePriorityUseCase createPriorityUseCase;
    @MockBean
    private PriorityRepository repository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void execute() {
        var rq = new PriorityRegistrationData(TITLE, COLOR);
        var priorityExpected = new Priority(ID_10, TITLE, COLOR, USER_ID);
        var newPriority = getNewPriority();
        when(repository.add(newPriority)).thenReturn(priorityExpected);

        var result = createPriorityUseCase.execute(rq, USER_ID);

        assertThat(result).isEqualTo(new PriorityPublicData(ID_10, TITLE, COLOR));
        verify(repository).add(newPriority);
    }

    private static @NotNull Priority getNewPriority() {
        var priority = Priority.builder();
        priority.title(TITLE);
        priority.color(COLOR);
        priority.userId(USER_ID);
        return priority.build();
    }
}